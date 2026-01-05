package com.inocencl.domain.service;

import com.inocencl.domain.model.Game;

public interface GameService {
    Game createGameWithPlayer(String playerUuid);

    Game createGameWithComputer(String playerUuid);

    Game joinGame(String gameUuid, String playerUuid);

    Iterable<Game> availableGames();

    Game gameByUuid(String uuid);

    Game processTurn(String gameUuid, String playerUuid, int x, int y);

    boolean isGameFinished(Game game);
}
