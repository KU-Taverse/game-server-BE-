package kutaverse.game.map.controller;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
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

    @Override
    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public Mono<PostMapUserResponse> addUser(@RequestBody PostMapUserRequest postMapUserRequest) {
        return userService.create(postMapUserRequest);
    }


    @GetMapping()
    @Override
    public Flux<User> getAllUser() {
        return userService.findAll();
    }

    @GetMapping("{userId}")
    @Override
    public Mono<User> getUser(@PathVariable(value = "userId") String userId) {
        return userService.findOne(userId);
    }

    @DeleteMapping("{userId}")
    public Mono<Long> deleteUser(@PathVariable(value = "userId") String userId) {
        return userService.deleteById(userId);
    }


    @PostMapping("/state/{userId}")
    public Mono<User> changeState(@PathVariable(value = "userId") String userId, @RequestParam("state")Status status) { return userService.changeState(userId,status); }

}
