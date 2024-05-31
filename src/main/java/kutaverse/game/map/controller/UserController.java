package kutaverse.game.map.controller;

import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import kutaverse.game.map.dto.response.PostMapUserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserController {


    Mono<PostMapUserResponse> addUser(PostMapUserRequest postMapUserRequest);


    Flux<User> getAllUser();


    Mono<User> getUser(String id);


    Mono<Long> deleteUser(String userId);
}
