package kutaverse.game.map.repository;

import kutaverse.game.map.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;


@RequiredArgsConstructor
@Repository
public class MysqlUserRepository implements UserRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<User> save(User user) {
        return get(user.getUserId())
                .flatMap(user1 -> update(user))
                .switchIfEmpty(r2dbcEntityTemplate.insert(user));
    }

    @Override
    public Mono<User> get(String userId) {
        return r2dbcEntityTemplate.select(Query.query(where("user_id").is(userId)), User.class)
                .single()
                .onErrorResume(e -> Mono.empty());
    }

    @Override
    public Flux<User> getAll() {
        return r2dbcEntityTemplate.select(Query.empty(), User.class);
    }

    @Override
    public Mono<Long> delete(String userId) {
        return r2dbcEntityTemplate.delete(Query.query(where("user_id").is(userId)), User.class)
                .then(Mono.just(1L));
    }

    public Mono<User> update(User user) {
        return r2dbcEntityTemplate.update(user);

    }
}
