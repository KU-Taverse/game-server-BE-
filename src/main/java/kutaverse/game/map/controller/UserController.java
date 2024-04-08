package kutaverse.game.map.controller;

import kutaverse.game.map.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserController {


    Mono<Boolean> addUser(User user);


    Flux<User> getAllUser();


    Mono<User> getUser(String id);


    Mono<Long> deleteUser(String key);
}
