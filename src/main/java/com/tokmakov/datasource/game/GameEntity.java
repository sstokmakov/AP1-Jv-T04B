package com.tokmakov.datasource.game;

import com.tokmakov.domain.model.GameStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Convert(converter = GameFieldConverter.class)
    @Column(name = "game_field", nullable = false, columnDefinition = "text")
    private int[][] gameField;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status", nullable = false)
    private GameStatus gameStatus;

    @Column(name = "player_x_uuid", nullable = false)
    private UUID playerXUuid;

    @Column(name = "player_o_uuid")
    private UUID playerOUuid;

    @Column(name = "current_turn_uuid")
    private UUID currentTurnPlayerUuid;

    @Column(name = "winner_uuid")
    private UUID winnerUuid;

    @Column(name = "vs_computer", nullable = false)
    private boolean vsComputer;

    protected GameEntity() {
    }
}