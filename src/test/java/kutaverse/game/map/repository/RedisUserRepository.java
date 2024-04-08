package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RedisUserRepository implements UserRepository{

    private final ReactiveRedisOperations<String, Object> redisOperations;
    @Override
    public Mono<Boolean> save(User user) {
        return redisOperations.opsForValue().set(user.getKey(),user);
    }

    @Override
    public Mono<Object> get(String key) {//왜 형변환을 할 수 없을까?
        return redisOperations.opsForValue().get(key);


    }

    @Override
    public Flux<User> getAll() {//왜 형변환을 할 수 있을까?
        return redisOperations.keys("*")
                .flatMap(value->redisOperations.opsForValue().get(value))
                .cast(User.class);
    }

    @Override
    public Mono<Long> delete(String key) {
        return redisOperations.delete(key);
    }
}
