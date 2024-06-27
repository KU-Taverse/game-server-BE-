package kutaverse.game.map.integration;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("[Integration Test] -User Repository Test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user = new User("1", 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 7.1, 8.1, 9.1, Status.STAND);
    User user1 = new User("1", 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 7.1, 8.1, 9.1, Status.STAND);
    User user2 = new User("2", 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 7.1, 8.1, 9.1, Status.STAND);

    @Test
    @DisplayName("user를 데이터베이스에 저장한다.")
    public void test1(){
        //given
        userRepository.save(user);
        //when
        Mono<User> findMonoUser = userRepository.findById(user.getUserId());
        //then
        StepVerifier.create(findMonoUser)
                .expectNext(user)
                .verifyComplete();
    }
}
