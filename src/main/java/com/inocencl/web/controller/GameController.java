package com.inocencl.web.controller;

import com.inocencl.domain.model.Game;
import com.inocencl.domain.auth.AppUserDetails;
import com.inocencl.domain.service.GameService;
import com.inocencl.web.mapper.GameDtoMapper;
import com.inocencl.web.model.GameDto;
import com.inocencl.web.model.GameCreateRequest;
import com.inocencl.web.model.GameUpdateRequest;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService service;
    private final GameDtoMapper mapper;

    @PostMapping("/create")
    public GameDto createGame(@RequestBody GameCreateRequest request,
                              @AuthenticationPrincipal AppUserDetails user) {
        Game game = request.isVsComputer()
                ? service.createGameWithComputer(user.getUuid().toString())
                : service.createGameWithPlayer(user.getUuid().toString());
        return mapper.toDto(game);
    }

    @GetMapping("/available")
    public List<GameDto> availableGames() {
        List<GameDto> result = new ArrayList<>();
        for (Game game : service.availableGames()) {
            result.add(mapper.toDto(game));
        }
        return result;
    }

    @PostMapping("/{uuid}/join")
    public GameDto joinGame(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid,
                            @AuthenticationPrincipal AppUserDetails user) {
        Game game = service.joinGame(uuid, user.getUuid().toString());
        return mapper.toDto(game);
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    public GameDto getGame(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid) {
        Game game = service.gameByUuid(uuid);
        return mapper.toDto(game);
    }

    @PostMapping("/{uuid}/move")
    public GameDto processTurn(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid,
                               @RequestBody GameUpdateRequest request,
                               @AuthenticationPrincipal AppUserDetails user) {
        Game game = service.processTurn(uuid, user.getUuid().toString(), request.getX(), request.getY());
        return mapper.toDto(game);
    }
}
