package kutaverse.game.map.service;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import kutaverse.game.map.dto.response.PostMapUserResponse;
import kutaverse.game.websocket.map.dto.request.UserRequestDto;
import kutaverse.game.map.repository.UserRepository;
import kutaverse.game.map.repository.util.RepositoryUtil;
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
    public Mono<PostMapUserResponse> create(PostMapUserRequest postMapUserRequest) {
        Mono<User> user = userRepository.save(postMapUserRequest.toEntity());
        return user.map(PostMapUserResponse::toDto);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.getAll();
    }

    /**
     *
     * @param length 시간의 길이 단위 초)
     * @return 시간 범위에 해당하는 NOTUSE가 아닌 유저를 반환
     */
    @Override
    public Flux<User> findAllByTime(long length) {
        if (length == RepositoryUtil.INFINITETIME)
            return findAll().filter(user -> user.getStatus() != Status.NOTUSE);

        return findAll().filter(user -> Duration.between(user.getLocalDateTime(), LocalDateTime.now()).toSeconds() < length && user.getStatus() != Status.NOTUSE);

    }

    ;

    @Override
    public Mono<User> findOne(String id) {
        return userRepository.get(id);
    }

    @Override
    public Mono<Long> deleteById(String userId) {
        return userRepository.delete(userId)
                .thenReturn(1L)
                .onErrorReturn(0L);
    }

    @Override
    public Mono<User> update(UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public Mono<User> changeState(String id, Status status) {
        return findOne(id)
                .map(user -> {
                    user.setStatus(status);
                    return user;
                });
    }

}