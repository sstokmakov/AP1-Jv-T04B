package com.tokmakov.domain.service.util;

import java.util.Arrays;

public class GameUtils {
    public static final int FIELD_SIZE = 3;

    public static final int EMPTY_CELL = 0;
    public static final int FIRST_PLAYER_CELL = 1;
    public static final int SECOND_PLAYER_CELL = 2;

    public static final char FIRST_PLAYER_SYMBOL = 'X';
    public static final char SECOND_PLAYER_SYMBOL = 'O';
    public static final char EMPTY_SYMBOL = ' ';


    public static int findWinnerCell(int[][] boardGame) {
        for (int i = 0; i < 3; i++) {
            if (boardGame[i][0] != 0 && boardGame[i][0] == boardGame[i][1] && boardGame[i][1] == boardGame[i][2])
                return boardGame[i][0];
            if (boardGame[0][i] != 0 && boardGame[0][i] == boardGame[1][i] && boardGame[1][i] == boardGame[2][i])
                return boardGame[0][i];
        }

        if (boardGame[0][0] != 0 && boardGame[0][0] == boardGame[1][1] && boardGame[1][1] == boardGame[2][2])
            return boardGame[0][0];
        if (boardGame[0][2] != 0 && boardGame[0][2] == boardGame[1][1] && boardGame[1][1] == boardGame[2][0])
            return boardGame[0][2];

        return EMPTY_CELL;
    }

    public static boolean isBoardFull(int[][] boardGame) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (boardGame[r][c] == EMPTY_CELL)
                    return false;
            }
        }
        return true;
    }

    public static int[][] createEmptyField() {
        int[][] field = new int[GameUtils.FIELD_SIZE][GameUtils.FIELD_SIZE];
        for (int i = 0; i < GameUtils.FIELD_SIZE; i++) {
            Arrays.fill(field[i], GameUtils.EMPTY_CELL);
        }
        return field;
    }
}
