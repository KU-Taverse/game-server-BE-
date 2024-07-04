package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserCashRepositoryImpl implements UserCashRepository {

    private final Map<String, User> userMap;
    private final UserRepository userRepository;

    /**
     * 캐시 조회
     * @param userId 유저 Id
     * @return Mono<User> 조회된 유저 Id
     */
    @Override
    public Mono<User> get(String userId) {
        return Mono.just(userMap.get(userId));
    }

    /**
     * 캐시 추가 또는 변경
     * @param user 유저
     * @return Mono<User> 이전 값또는 현재값(새로 추가된 경우)
     */
    @Override
    public Mono<User> add(User user) {
        return Mono.justOrEmpty(userMap.put(user.getUserId(), user))
                .switchIfEmpty(Mono.just(user));
    }

    /**
     * 캐시 전부 조회
     * @return Flux<User>
     */
    @Override
    public Flux<User> getAll() {
        return Flux.fromIterable(userMap.values());
    }

    /**
     * 캐시 전부 flush(캐시 값을 지우지 않는다)
     * @return flush 성공 여부
     */
    @Override
    public Mono<Boolean> flush() {
        return Flux.fromIterable(userMap.values())
                .flatMap(userRepository::save)
                .then(Mono.just(true))
                .onErrorReturn(false);
    }

    /**
     * 특정 user flush(캐시 값을 지운다)
     * @param userId
     * @return 성공 여부
     */
    @Override
    public Mono<Boolean> flushById(String userId) {
        return userRepository.save(userMap.get(userId))
                .map(user -> {
                    userMap.remove(userId);
                    return true;
                })
                .onErrorReturn(false);
    }

    /**
     * 캐시 조회
     * @param userId 유저Id
     * @return 조회 값
     */
    @Override
    public Mono<User> find(String userId) {
        return null;
    }

    /**
     * 캐시 삭제
     * @param userId
     * @return 삭제 성공 여부
     */
    @Override
    public Mono<Boolean> delete(String userId) {
        userMap.remove(userId);
        return Mono.just(Boolean.TRUE);
    }

    /**
     * 캐시 전부 삭제
     * @return 삭제 성공 여부
     */
    @Override
    public Mono<Boolean> deleteAll() {
        return null;
    }
}
