package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class HashMapUserRepository implements UserRepository {

    public final Map<String,User> userMap;

    @Override
    public Mono<User> save(User user) {
        return Mono.justOrEmpty(userMap.put(user.getUserId(),user))
                .switchIfEmpty(Mono.just(user));
    }

    @Override
    public Mono<User> get(String userId) {
        return Mono.just(userMap.get(userId));
    }

    @Override
    public Flux<User> getAll() {
        return Flux.fromIterable(userMap.values());
    }

    @Override
    public Mono<Long> delete(String userId) {
        userMap.remove(userId);
        return Mono.just(1L);
    }

    @Override
    public Mono<User> update(User user) {
        return Mono.just(userMap.put(user.getUserId(),user));
    }
}
