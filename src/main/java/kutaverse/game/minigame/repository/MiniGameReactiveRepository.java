package kutaverse.game.minigame.repository;

import kutaverse.game.minigame.domain.GameResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface MiniGameReactiveRepository extends ReactiveCrudRepository<GameResult,Long> {

    Mono<GameResult> findByUserId(String userId);
}
