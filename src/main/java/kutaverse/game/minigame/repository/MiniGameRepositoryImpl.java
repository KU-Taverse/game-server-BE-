package kutaverse.game.minigame.repository;

import kutaverse.game.minigame.domain.GameResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MiniGameRepositoryImpl implements MiniGameRepository{
    private final MiniGameReactiveRepository mgrRepository;

    @Override
    public Mono<GameResult> findByRoomId(String id) {
        return mgrRepository.findByRoomId(id);
    }

    @Override
    public Mono<GameResult> save(GameResult gameResult) {
        return mgrRepository.save(gameResult);
    }

    @Override
    public Mono<GameResult> update(GameResult gameResult) {
        return mgrRepository.save(gameResult);
    }

    @Override
    public Flux<GameResult> findByPlayerId(String userId) {
        return mgrRepository.findByPlayer1Id(userId);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return mgrRepository.deleteById(id);
    }
}
