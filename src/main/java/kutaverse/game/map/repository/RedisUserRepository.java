package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Repository
public class RedisUserRepository implements UserRepository{

    private final ReactiveRedisOperations<String, User> redisOperations;
    @Override
    public Mono<User> save(User user) {
        return redisOperations.opsForValue()
                .get(user.getKey())
                .flatMap(findUser -> {
                    user.updateTime();
                    return redisOperations.opsForValue()
                            .set(user.getKey(), user)
                            .then(Mono.just(user));

                })
                .switchIfEmpty( // If user doesn't exist, save it as new
                        redisOperations.opsForValue()
                                .set(user.getKey(), user)
                                .then(Mono.just(user))
                );
    }
    @Override
    public Mono<User> get(String key) {
        return redisOperations.opsForValue().get(key);


    }

    @Override
    public Flux<User> getAll() {
        return redisOperations.keys("*")
                .flatMap(value->redisOperations.opsForValue().get(value));

    }

    @Override
    public Mono<Long> delete(String key) {
        return redisOperations.delete(key);
    }

    @Override
    public Mono<User>update(User user){
        return Mono.just(user);
    }
}
