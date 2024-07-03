package kutaverse.game.map.integration;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.repository.MysqlUserRepository;
import kutaverse.game.map.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
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

/*
    @Test
    @DisplayName("user를 데이터베이스에 저장한다.")
    public void test1(){
        //given
        userRepository.save(user);
        //when
        Mono<User> findMonoUser = userRepository.get(user.getUserId());
        //then
        StepVerifier.create(findMonoUser)
                .expectNext(user)
                .verifyComplete();
        //왜 localDateTime 문제가 생기지?
    }
*/

/*    @Test
    public void test2(){
        User user = new User("11", 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 7.1, 8.1, 9.1, Status.STAND);

        userRepository.save(user)
                .subscribe(savedUser -> System.out.println("User saved: " + savedUser),
                        error -> System.err.println("Error: " + error));
    }

    @Test
    public void testSaveWithDuplicateKey() {
        User user = new User("1", 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 7.1, 8.1, 9.1, Status.STAND);

        // 첫 번째 호출로 '1' ID가 삽입됩니다.
        userRepository.save(user)
                .doOnNext(savedUser -> System.out.println("User saved: " + savedUser))
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                .block(); // 블로킹으로 첫 호출의 완료를 기다림

        // 동일한 ID로 두 번째 호출을 하면 중복 키 오류가 발생해야 합니다.
        userRepository.save(user)
                .doOnNext(savedUser -> System.out.println("User saved after retry: " + savedUser))
                .doOnError(error -> System.err.println("Error occurred during retry: " + error.getMessage()))
                .block(); // 블로킹으로 두 번째 호출의 완료를 기다림
    }*/
}
