package com.tokmakov.datasource.game;

import com.tokmakov.domain.model.Game;

public class GameEntityMapper {
    public static GameEntity toGameEntity(Game game) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setUuid(game.getUuid());
        gameEntity.setGameField(game.getGameField());
        gameEntity.setGameStatus(game.getGameStatus());
        gameEntity.setPlayerXUuid(game.getPlayerXUuid());
        gameEntity.setPlayerOUuid(game.getPlayerOUuid());
        gameEntity.setCurrentTurnPlayerUuid(game.getCurrentTurnPlayerUuid());
        gameEntity.setWinnerUuid(game.getWinnerUuid());
        gameEntity.setVsComputer(game.isVsComputer());
        return gameEntity;
    }

    public static Game toGame(GameEntity gameEntity) {
        Game game = new Game();
        game.setUuid(gameEntity.getUuid());
        game.setGameField(gameEntity.getGameField());
        game.setGameStatus(gameEntity.getGameStatus());
        game.setPlayerXUuid(gameEntity.getPlayerXUuid());
        game.setPlayerOUuid(gameEntity.getPlayerOUuid());
        game.setCurrentTurnPlayerUuid(gameEntity.getCurrentTurnPlayerUuid());
        game.setWinnerUuid(gameEntity.getWinnerUuid());
        game.setVsComputer(gameEntity.isVsComputer());
        return game;
    }
}
