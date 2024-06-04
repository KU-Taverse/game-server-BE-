package kutaverse.game.minigame.service;

import kutaverse.game.minigame.domain.GameResult;
import kutaverse.game.websocket.minigame.dto.GameResultDTO;
import kutaverse.game.websocket.minigame.dto.GameUpdateDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MiniGameService {
    Mono<GameResult> createGameResult(GameResultDTO gameResultDTO);

    Mono<GameResult> updateGameResult(GameUpdateDTO gameUpdateDTO);

    Mono<Void> deleteGameResult(Long id);

    Flux<GameResult> findGameResultsByPlayerId(String playerId);

    Mono<GameResult> findGameResultById(String id);
}
