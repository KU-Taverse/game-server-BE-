package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);

    Mono<User> get(String userId);

    Flux<User> getAll();

    Mono<Long> delete(String userId);

    Mono<User> update(User user);
}
