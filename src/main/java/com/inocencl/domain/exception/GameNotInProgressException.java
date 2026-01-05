package com.inocencl.domain.exception;

import com.inocencl.domain.model.GameStatus;

public class GameNotInProgressException extends RuntimeException {
    public GameNotInProgressException(GameStatus status) {
        super("Game is not in progress. Current status: " + status);
    }
}
