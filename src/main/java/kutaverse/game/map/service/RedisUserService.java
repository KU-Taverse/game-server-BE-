package kutaverse.game.map.service;

import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.UserRequestDto;
import kutaverse.game.map.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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