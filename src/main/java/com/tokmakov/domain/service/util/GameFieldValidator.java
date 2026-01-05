package com.tokmakov.domain.service.util;

import com.tokmakov.domain.exception.CellAlreadyOccupiedException;
import com.tokmakov.domain.exception.CoordinatesOutOfBoundsException;
import com.tokmakov.domain.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameFieldValidator {
    public void validateTurn(Game game, int x, int y) {
        if (x < 0 || y < 0 || x >= GameUtils.FIELD_SIZE || y >= GameUtils.FIELD_SIZE)
            throw new CoordinatesOutOfBoundsException(x, y, GameUtils.FIELD_SIZE);
        if (game.getGameField()[y][x] != GameUtils.EMPTY_CELL)
            throw new CellAlreadyOccupiedException(x, y);
    }
}
