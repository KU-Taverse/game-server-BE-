package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@RequiredArgsConstructor
public class RedisUserRepository  {

    private final ReactiveRedisOperations<String, User> redisOperations;


    public Mono<User> save(User user) {
        return redisOperations.opsForValue()
                .set(user.getUserId(), user)
                .then(Mono.just(user));
    }


    public Mono<User> get(String userId) {
        return redisOperations.opsForValue().get(userId);
    }


    public Flux<User> getAll() {
        return redisOperations.keys("*")
                .flatMap(value -> redisOperations.opsForValue().get(value));
    }


    public Mono<Long> delete(String userId) {
        return redisOperations.delete(userId);
    }


    public Mono<User> update(User user) {
        return Mono.just(user);
    }
}
