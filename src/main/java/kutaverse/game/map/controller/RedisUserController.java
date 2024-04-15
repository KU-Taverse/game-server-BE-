package kutaverse.game.map.controller;

import kutaverse.game.map.domain.User;
import kutaverse.game.map.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RedisUserController implements UserController{

    private final UserService userService;

    @PostMapping("/user")
    @Override
    public Mono<User> addUser(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/user")
    @Override
    public Flux<User> getAllUser() {
        return userService.findAll();
    }

    @GetMapping("/user/{key}")
    @Override
    public Mono<User> getUser(@PathVariable(value = "key") String key) {
        return userService.findOne(key);
    }

    @DeleteMapping("/user/{key}")
    public Mono<Long> deleteUser(@PathVariable(value = "key") String key) {
        return userService.deleteById(key);
    }
}
