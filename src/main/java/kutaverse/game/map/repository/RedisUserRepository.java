package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
public class RedisUserRepository implements UserRepository{

    private final ReactiveRedisOperations<String, User> redisOperations;
    @Override
    public Mono<Boolean> save(User user) {
        return redisOperations.opsForValue().set(user.getKey(),user);
    }

    @Override
    public Mono<User> get(String key) {
        return redisOperations.opsForValue().get(key);


    }

    @Override
    public Flux<User> getAll() {
        return redisOperations.keys("*")
                .flatMap(value->redisOperations.opsForValue().get(value))
                .cast(User.class);
    }

    @Override
    public Mono<Long> delete(String key) {
        return redisOperations.delete(key);
    }
}
