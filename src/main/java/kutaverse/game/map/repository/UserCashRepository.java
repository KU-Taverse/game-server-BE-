package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 캐시 repository 추상화
 * 저장,삭제,조회 할 수 있어야한다.
 * flush 할 수 있어야한다.
 */
public interface UserCashRepository {

    Mono<User> get(String userId);

    Mono<User> add(User user);

    Flux<User> getAll();

    Mono<Boolean> flush();

    Mono<Boolean> flushById(String userId);

    Mono<User> find(String userId);

    Mono<Boolean> delete(String userId);

    Mono<Boolean> deleteAll();


}
