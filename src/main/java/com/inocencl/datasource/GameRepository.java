package com.inocencl.datasource;

import com.inocencl.domain.model.Game;
import com.inocencl.domain.model.GameStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<Game, UUID> {
    Iterable<Game> findByGameStatus(GameStatus status);
}
