package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class RedisUserRepository implements UserRepository {

    private final ReactiveRedisOperations<String, User> redisOperations;

    @Override
    public Mono<User> save(User user) {
        return redisOperations.opsForValue()
                .set(user.getUserId(), user)
                .then(Mono.just(user));
    }

    @Override
    public Mono<User> get(String userId) {
        return redisOperations.opsForValue().get(userId);
    }

    @Override
    public Flux<User> getAll() {
        return redisOperations.keys("*")
                .flatMap(value -> redisOperations.opsForValue().get(value));
    }

    @Override
    public Mono<Long> delete(String userId) {
        return redisOperations.delete(userId);
    }

    @Override
    public Mono<User> update(User user) {
        return Mono.just(user);
    }
}
