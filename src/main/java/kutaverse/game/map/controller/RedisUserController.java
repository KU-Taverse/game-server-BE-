package kutaverse.game.map.controller;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import kutaverse.game.map.dto.response.GetMapUserResponse;
import kutaverse.game.map.dto.response.PostMapUserResponse;
import kutaverse.game.map.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class RedisUserController implements UserController{

    private final UserService userService;

    /**
     * 맵 유저 저장
     * @param postMapUserRequest 맵 유저 정보
     * @return Mono<PostMapUserResponse> 저장된 맵 유저 정보
     */
    @Override
    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public Mono<PostMapUserResponse> addUser(@RequestBody PostMapUserRequest postMapUserRequest) {
        return userService.create(postMapUserRequest);
    }

    /**
     * 맵 유저 모두 조회
     * @return Flux<GetMapUserResponse> Flux<맵 유저 정보></맵>
     */
    @GetMapping()
    @Override
    public Flux<GetMapUserResponse> getAllUser() {
        return userService.findAll();
    }

    /**
     * 맵 유저 조회
     * @param userId 유저 Id
     * @return Mono<GetMapUserResponse> 맵 유저 정보
     */
    @GetMapping("{userId}")
    @Override
    public Mono<GetMapUserResponse> getUser(@PathVariable(value = "userId") String userId) {
        return userService.findOne(userId);
    }

    /**
     * 맵 유저 삭제
     * @param userId 유저 Id
     * @return Mono<Long> 삭제된 유저 Id
     */
    @DeleteMapping("{userId}")
    public Mono<Long> deleteUser(@PathVariable(value = "userId") String userId) {
        return userService.deleteById(userId);
    }

    /**
     * TODO 구현안됨
     * 맵 유저 상태 변경
     * @param userId 유저 Id
     * @param status 변경할 상태
     * @return 변경된 유저 정보
     */
    @PostMapping("/state/{userId}")
    public Mono<User> changeState(@PathVariable(value = "userId") String userId, @RequestParam("state")Status status) { return userService.changeState(userId,status); }

}
