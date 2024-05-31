package kutaverse.game.map.integration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import kutaverse.game.map.controller.RedisUserController;
import kutaverse.game.map.controller.UserController;
import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UserRepository userRepository;

    User user1=new User("1",2.1,1.1,1.1,1.1,1.1,1.1, Status.JUMP);
    User user2=new User("2",1.1,1.1,1.1,1.1,1.1,1.1, Status.STAND);

    @PostConstruct
    public void initDB(){
        Mono<Void> saveUsers = userRepository.save(user1)
                .then(userRepository.save(user2))
                .then();
        StepVerifier.create(saveUsers)
                .expectSubscription()
                .verifyComplete();
    }


    @Test
    @DisplayName("get 요청으로 유저를 가져올 수 있다.")
    public void test1(){
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).configureClient().responseTimeout(Duration.ofHours(1)).build();
        webTestClient.get()
                .uri("/user/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(User.class)
                .isEqualTo(user1);
    }

    /**
     * TODO
     * 예외 처리에 대한 구현이 필요 함(현재 예외 처리 안됨)
     */
    @Test
    @DisplayName("get 요청으로 db에 저장되어 있지 않은 유저는 가져올 수 없다")
    public void test2(){
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).configureClient().responseTimeout(Duration.ofHours(1)).build();
        webTestClient.get()
                .uri("/user/3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }
}
