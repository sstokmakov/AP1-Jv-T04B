package com.tokmakov.domain.service;

import com.tokmakov.datasource.GameRepository;
import com.tokmakov.domain.exception.*;
import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.service.util.GameFieldValidator;
import com.tokmakov.domain.service.util.GameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private static final UUID COMPUTER_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private final GameRepository repository;
    private final ComputerMoveStrategy computerLogicService;
    private final GameFieldValidator fieldValidator;

    @Override
    public Game createGameWithPlayer(String playerUuid) {
        int[][] field = GameUtils.createEmptyField();
        Game game = new Game(UUID.randomUUID(), field);

        game.setPlayerXUuid(UUID.fromString(playerUuid));
        game.setPlayerOUuid(null);
        game.setVsComputer(false);
        game.setGameStatus(GameStatus.WAITING_FOR_PLAYERS);
        game.setCurrentTurnPlayerUuid(null);
        repository.save(game);
        return game;
    }

    @Override
    public Game createGameWithComputer(String playerUuid) {
        int[][] field = GameUtils.createEmptyField();
        Game game = new Game(UUID.randomUUID(), field);
        game.setPlayerXUuid(UUID.fromString(playerUuid));
        game.setPlayerOUuid(COMPUTER_UUID);
        game.setVsComputer(true);
        game.setGameStatus(GameStatus.IN_PROGRESS);
        game.setCurrentTurnPlayerUuid(UUID.fromString(playerUuid));
        repository.save(game);
        return game;
    }

    @Override
    public Game joinGame(String gameUuid, String playerUuid) {
        Game game = getGameByUuid(gameUuid);
        if (game.getGameStatus() != GameStatus.WAITING_FOR_PLAYERS || game.isVsComputer()) {
            throw new GameNotInProgressException(game.getGameStatus());
        }
        if (game.getPlayerOUuid() != null) {
            throw new IllegalArgumentException("Game already has two players");
        }
        if (game.getPlayerXUuid().toString().equals(playerUuid)) {
            throw new IllegalArgumentException("Player already joined");
        }
        game.setPlayerOUuid(UUID.fromString(playerUuid));
        game.setGameStatus(GameStatus.IN_PROGRESS);
        game.setCurrentTurnPlayerUuid(game.getPlayerXUuid());
        repository.save(game);
        return game;
    }

    @Override
    public Iterable<Game> availableGames() {
        return repository.findByGameStatus(GameStatus.WAITING_FOR_PLAYERS);
    }

    @Override
    public boolean isGameFinished(Game game) {
        return game.getGameStatus() == GameStatus.GAME_OVER;
    }

    @Override
    public Game gameByUuid(String uuid) {
        return getGameByUuid(uuid);
    }

    @Override
    public Game processTurn(String gameUuid, String playerUuid, int x, int y) {
        Game game = getGameByUuid(gameUuid);
        if (game.getGameStatus() == GameStatus.WAITING_FOR_PLAYERS) {
            throw new GameNotInProgressException(game.getGameStatus());
        }
        if (isGameFinished(game)) {
            throw new GameNotInProgressException(game.getGameStatus());
        }

        UUID playerId = UUID.fromString(playerUuid);
        if (!playerId.equals(game.getCurrentTurnPlayerUuid())) {
            throw new IllegalArgumentException("It is not this player's turn");
        }

        int playerCell = resolvePlayerCell(game, playerId);
        applyMove(game, x, y, playerCell);
        updateGameState(game);

        if (!isGameFinished(game) && game.isVsComputer() && COMPUTER_UUID.equals(game.getCurrentTurnPlayerUuid())) {
            makeComputerMove(game);
        }
        return game;
    }

    public void makeComputerMove(Game game) {
        if (isGameFinished(game)) {
            throw new GameNotInProgressException(game.getGameStatus());
        }
        int[] move = computerLogicService.findMove(game.getGameField());
        applyMove(game, move[0], move[1], GameUtils.SECOND_PLAYER_CELL);
        updateGameState(game);
    }

    private void applyMove(Game game, int x, int y, int value) {
        fieldValidator.validateTurn(game, x, y);
        game.updateField(x, y, value);
        repository.save(game);
    }

    private void updateGameState(Game game) {
        int winnerCell = GameUtils.findWinnerCell(game.getGameField());
        if (winnerCell != GameUtils.EMPTY_CELL) {
            game.setGameStatus(GameStatus.GAME_OVER);
            game.setWinnerUuid(resolveWinnerUuid(game, winnerCell));
            game.setCurrentTurnPlayerUuid(null);
            repository.save(game);
            return;
        }
        if (GameUtils.isBoardFull(game.getGameField())) {
            game.setGameStatus(GameStatus.GAME_OVER);
            game.setWinnerUuid(null);
            game.setCurrentTurnPlayerUuid(null);
            repository.save(game);
            return;
        }

        UUID nextPlayer = nextTurnPlayer(game);
        game.setGameStatus(GameStatus.IN_PROGRESS);
        game.setCurrentTurnPlayerUuid(nextPlayer);
        repository.save(game);
    }

    private UUID resolveWinnerUuid(Game game, int winnerCell) {
        return winnerCell == GameUtils.FIRST_PLAYER_CELL ? game.getPlayerXUuid() : game.getPlayerOUuid();
    }

    private int resolvePlayerCell(Game game, UUID playerId) {
        if (playerId.equals(game.getPlayerXUuid())) {
            return GameUtils.FIRST_PLAYER_CELL;
        }
        if (playerId.equals(game.getPlayerOUuid())) {
            return GameUtils.SECOND_PLAYER_CELL;
        }
        throw new IllegalArgumentException("Player is not part of this game");
    }

    private UUID nextTurnPlayer(Game game) {
        UUID current = game.getCurrentTurnPlayerUuid();
        return current.equals(game.getPlayerOUuid()) ?
                game.getPlayerXUuid() : game.getPlayerOUuid();
    }

    private Game getGameByUuid(String uuid) {
        UUID parsedUuid = UUID.fromString(uuid);
        Optional<Game> game = repository.findById(parsedUuid);
        return game.orElseThrow(() -> new GameNotFoundException(uuid));
    }
}
