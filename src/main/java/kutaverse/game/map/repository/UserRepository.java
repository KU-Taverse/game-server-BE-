package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<Boolean> save(User user);

    Mono<Object> get(String key);

    Flux<User> getAll();

    Mono<Long> delete(String key);
}
