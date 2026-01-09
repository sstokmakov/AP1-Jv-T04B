package com.tokmakov.domain.model;

import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

@Getter
@Setter
public class Game {
    private UUID uuid;
    private int[][] gameField;
    private GameStatus gameStatus;
    private UUID playerXUuid;
    private UUID playerOUuid;
    private UUID currentTurnPlayerUuid;
    private UUID winnerUuid;
    private boolean vsComputer;

    public Game(UUID uuid, int[][] gameField) {
        this.uuid = uuid;
        this.gameField = gameField;
        gameStatus = GameStatus.WAITING_FOR_PLAYERS;
    }

    public Game() {
    }

    public int[][] getGameField() {
        int[][] copy = new int[gameField.length][gameField[0].length];
        for (int i = 0; i < gameField.length; i++) {
            System.arraycopy(gameField[i], 0, copy[i], 0, gameField[i].length);
        }
        return copy;
    }

    public void updateField(int x, int y, int value) {
        gameField[y][x] = value;
    }
}
