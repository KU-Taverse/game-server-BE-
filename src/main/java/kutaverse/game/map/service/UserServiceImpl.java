package kutaverse.game.map.service;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.response.GetMapUserResponse;
import kutaverse.game.map.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> create(User user) {
        return null;
    }

    @Override
    public Flux<User> findAll() {
        return null;
    }

    @Override
    public Flux<User> findAllByTime(long length) {
        return null;
    }

    @Override
    public Mono<GetMapUserResponse> findOne(String userId) {
        return userRepository.getByUserId(userId).map(GetMapUserResponse::toDto);
    }

    @Override
    public Mono<Long> deleteById(String userId) {
        return null;
    }

    @Override
    public Mono<User> update(User user) {
        return null;
    }

    @Override
    public Mono<User> changeState(String id, Status status) {
        return null;
    }
}
