package com.inocencl.web.mapper;

import com.inocencl.domain.model.Game;
import com.inocencl.web.model.GameDto;
import org.springframework.stereotype.Component;

@Component
public class GameDtoMapper {
    public GameDto toDto(Game game) {
        return new GameDto(
                game.getUuid(),
                game.getGameField(),
                game.getGameStatus(),
                game.getPlayerXUuid(),
                game.getPlayerOUuid(),
                game.getCurrentTurnPlayerUuid(),
                game.getWinnerUuid(),
                game.isVsComputer()
        );
    }
}
