package kutaverse.game.map.service;

import kutaverse.game.map.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> create(User user);

    Flux<User> findAll();

    Mono<User> findOne(String key);

    Mono<Long> deleteById(String key);
}
