package com.inocencl.web.model;

import com.inocencl.domain.model.GameStatus;
import com.inocencl.domain.service.util.GameUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class GameDto {
    private final UUID uuid;
    private final Character[][] gameField;
    private final GameStatus gameStatus;
    private final UUID playerXUuid;
    private final UUID playerOUuid;
    private final UUID currentTurnPlayerUuid;
    private final UUID winnerUuid;
    private final boolean vsComputer;
    private final char playerXSymbol;
    private final char playerOSymbol;

    public GameDto(UUID uuid,
                   int[][] gameField,
                   GameStatus gameStatus,
                   UUID playerXUuid,
                   UUID playerOUuid,
                   UUID currentTurnPlayerUuid,
                   UUID winnerUuid,
                   boolean vsComputer) {
        this.uuid = uuid;
        this.gameField = new Character[gameField.length][gameField.length];
        this.gameStatus = gameStatus;
        this.playerXUuid = playerXUuid;
        this.playerOUuid = playerOUuid;
        this.currentTurnPlayerUuid = currentTurnPlayerUuid;
        this.winnerUuid = winnerUuid;
        this.vsComputer = vsComputer;
        this.playerXSymbol = GameUtils.FIRST_PLAYER_SYMBOL;
        this.playerOSymbol = GameUtils.SECOND_PLAYER_SYMBOL;

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                this.gameField[i][j] = getSymbol(gameField[i][j]);
            }
        }
    }

    public List<String> getGameField() {
        return Arrays.stream(gameField)
                .map(this::rowToString)
                .toList();
    }

    private String rowToString(Character[] chars) {
        StringBuilder sb = new StringBuilder();
        for (Character ch :chars){
            sb.append(ch);
        }
        return sb.toString();
    }

    private char getSymbol(int n) {
        return switch (n) {
            case GameUtils.SECOND_PLAYER_CELL -> GameUtils.SECOND_PLAYER_SYMBOL;
            case GameUtils.FIRST_PLAYER_CELL -> GameUtils.FIRST_PLAYER_SYMBOL;
            default -> GameUtils.EMPTY_SYMBOL;
        };
    }
}
