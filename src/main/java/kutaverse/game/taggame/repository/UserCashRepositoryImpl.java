package kutaverse.game.taggame.repository;

import kutaverse.game.taggame.domain.TagGameUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserCashRepositoryImpl {

    private final Map<String, TagGameUser> tagGameUserMap;

    /**
     * taggame 유저 조회
     * @param userId 유저 Id
     * @return Mono<User> 조회된 유저 Id
     */
    public Mono<TagGameUser> get(String userId) {
        return Mono.just(tagGameUserMap.get(userId));
    }

    /**
     * taggame 유저 추가 및 변경
     * @param tagGameUser 유저
     * @return Mono<User> 이전 값또는 현재값(새로 추가된 경우)
     */
    public Mono<TagGameUser> add(TagGameUser tagGameUser) {
        return Mono.justOrEmpty(tagGameUserMap.put(tagGameUser.getUserId(), tagGameUser))
                .switchIfEmpty(Mono.just(tagGameUser));
    }

    /**
     * taggame 유저 전부 조회
     * @return Flux<User>
     */
    public Flux<TagGameUser> getAll() {
        return Flux.fromIterable(tagGameUserMap.values());
    }


    /**
     * taggame 유저 삭제
     * @param userId
     * @return 삭제 성공 여부
     */
    public Mono<Boolean> delete(String userId) {
        tagGameUserMap.remove(userId);
        return Mono.just(Boolean.TRUE);
    }
}
