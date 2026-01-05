package com.inocencl.domain.model;

import com.inocencl.datasource.GameFieldConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "games")
public class Game {
    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Setter
    @Convert(converter = GameFieldConverter.class)
    @Column(name = "game_field", nullable = false, columnDefinition = "text")
    private int[][] gameField;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "game_status", nullable = false)
    private GameStatus gameStatus;

    @Setter
    @Column(name = "player_x_uuid", nullable = false)
    private UUID playerXUuid;

    @Setter
    @Column(name = "player_o_uuid")
    private UUID playerOUuid;

    @Setter
    @Column(name = "current_turn_uuid")
    private UUID currentTurnPlayerUuid;

    @Setter
    @Column(name = "winner_uuid")
    private UUID winnerUuid;

    @Setter
    @Column(name = "vs_computer", nullable = false)
    private boolean vsComputer;

    protected Game() {
    }

    public Game(UUID uuid, int[][] gameField) {
        this.uuid = uuid;
        this.gameField = gameField;
        gameStatus = GameStatus.WAITING_FOR_PLAYERS;
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
