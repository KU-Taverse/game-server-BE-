package kutaverse.game.map.unit;

import kutaverse.game.map.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataRedisTest
public class RedisUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
}
