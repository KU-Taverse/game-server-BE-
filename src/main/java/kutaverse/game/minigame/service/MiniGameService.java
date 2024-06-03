package kutaverse.game.minigame.service;

import kutaverse.game.minigame.domain.GameResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MiniGameService {
    Mono<GameResult> createGameResult(GameResult gameResult);

    Mono<GameResult> updateGameResult(GameResult gameResult);

    Mono<Void> deleteGameResult(Long id);

    Flux<GameResult> findGameResultsByUserId(String userId);

    Mono<GameResult> findGameResultById(Long id);
}
