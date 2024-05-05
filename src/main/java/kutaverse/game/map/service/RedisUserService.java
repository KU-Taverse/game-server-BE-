package kutaverse.game.map.service;

import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.UserRequestDto;
import kutaverse.game.map.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RedisUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> create(User user) {
        return userRepository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.getAll();
    }

    @Override
    public Flux<User> findAllByTime(long length){
        if(length==0)
            return findAll();
        //10
        Flux<User> userFlux=findAll().filter(user -> Duration.between(user.getLocalDateTime(), LocalDateTime.now()).toSeconds() < length);
        return userFlux;
    };

    @Override
    public Mono<User> findOne(String id) {
        return userRepository.get(id);
    }

    @Override
    public Mono<Long> deleteById(String key) {
        return userRepository.delete(key);
    }

    @Override
    public Mono<User> update(UserRequestDto userRequestDto) {
        return create(userRequestDto.toEntity());
    }

}