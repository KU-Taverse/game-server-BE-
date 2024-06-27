package kutaverse.game.map.service;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import kutaverse.game.map.dto.response.GetMapUserResponse;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<PostMapUserResponse> create(PostMapUserRequest postMapUserRequest) {
        return userRepository.save(postMapUserRequest.toEntity())
                .map(PostMapUserResponse::toDto);
    }

    @Override
    public Flux<GetMapUserResponse> findAll() {
        return userRepository.getAll()
                .map(GetMapUserResponse::toDto);
    }

    /**
     *
     * @param length 시간의 길이 단위 초)
     * @return 시간 범위에 해당하는 NOTUSE가 아닌 유저를 반환
     */
    @Override
    public Flux<User> findAllByTime(long length) {
        if (length == RepositoryUtil.INFINITE_TIME)
            return userRepository.getAll().filter(user -> user.getStatus() != Status.NOTUSE);
        return userRepository.getAll().filter(user -> Duration.between(user.getLocalDateTime(), LocalDateTime.now()).toSeconds() < length && user.getStatus() != Status.NOTUSE);

    }

    ;

    @Override
    public Mono<GetMapUserResponse> findOne(String id) {
        return userRepository.get(id)
                .map(GetMapUserResponse::toDto);
    }

    @Override
    public Mono<Long> deleteById(String userId) {
        return userRepository.delete(userId)
                .thenReturn(1L)
                .onErrorReturn(0L);
    }

    @Override
    public Mono<User> update(UserRequestDto userRequestDto) {
        return userRepository.save(userRequestDto.toEntity());
    }

    @Override
    public Mono<User> changeState(String id, Status status) {
        return null;
    }

}