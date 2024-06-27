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
public class HashMapUserRepository implements UserCashRepository {

    public final Map<String, User> userMap;


    @Override
    public Mono<User> get(String userId) {
        return Mono.just(userMap.get(userId));
    }

    @Override
    public Mono<User> add(User user) {
        return Mono.justOrEmpty(userMap.put(user.getUserId(), user))
                .switchIfEmpty(Mono.just(user));
    }

    @Override
    public Flux<User> getAll() {
        return Flux.fromIterable(userMap.values());
    }

    @Override
    public Mono<Boolean> flush() {
        return null;
    }

    @Override
    public Mono<User> find(String userId) {
        return null;
    }

    @Override
    public Mono<Boolean> delete(String userId) {
        userMap.remove(userId);
        return Mono.just(Boolean.TRUE);
    }

    @Override
    public Mono<Boolean> deleteAll() {
        return null;
    }
}
