package kutaverse.game.map.service;

import kutaverse.game.map.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Boolean> create(User user);

    Flux<User> getAll();

    Mono<Object> getOne(String key);

    Mono<Long> deleteById(String key);
}
