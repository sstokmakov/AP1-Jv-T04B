package com.tokmakov.domain.service;

import com.tokmakov.domain.model.Game;

import java.util.List;

public interface GameService {
    Game createGameWithPlayer(String playerUuid);

    Game createGameWithComputer(String playerUuid);

    Game joinGame(String gameUuid, String playerUuid);

    List<String> availableGames(String userUuid);

    Game gameByUuid(String uuid);

    Game processTurn(String gameUuid, String playerUuid, int x, int y);

    boolean isGameFinished(Game game);
}
