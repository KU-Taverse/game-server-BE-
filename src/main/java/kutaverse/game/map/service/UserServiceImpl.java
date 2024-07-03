package kutaverse.game.map.service;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserServiceImpl implements UserService{
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
    public Mono<User> findOne(String userId) {
        return null;
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
