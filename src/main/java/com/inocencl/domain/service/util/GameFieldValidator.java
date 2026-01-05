package com.inocencl.domain.service.util;

import com.inocencl.domain.exception.CellAlreadyOccupiedException;
import com.inocencl.domain.exception.CoordinatesOutOfBoundsException;
import com.inocencl.domain.model.Game;
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
