package kutaverse.game.map.service;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import kutaverse.game.map.dto.response.GetMapUserResponse;
import kutaverse.game.map.dto.response.PostMapUserResponse;
import kutaverse.game.websocket.map.dto.request.UserRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<PostMapUserResponse> create(PostMapUserRequest postMapUserRequest);

    Flux<GetMapUserResponse> findAll();

    Flux<User> findAllByTime(long length);

    Mono<GetMapUserResponse> findOne(String userId);

    Mono<Long> deleteById(String userId);

    Mono<User> update(UserRequestDto userRequestDto);

    Mono<User> changeState(String id, Status status);
}
