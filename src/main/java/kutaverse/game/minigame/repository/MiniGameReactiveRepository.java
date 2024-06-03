package kutaverse.game.minigame.repository;

import kutaverse.game.minigame.domain.GameResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

public interface MiniGameReactiveRepository extends ReactiveCrudRepository<GameResult,Long> {

}
