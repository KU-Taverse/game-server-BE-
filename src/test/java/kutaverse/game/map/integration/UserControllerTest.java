package kutaverse.game.map.integration;

import jakarta.annotation.PostConstruct;
import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import kutaverse.game.map.dto.response.GetMapUserResponse;

import kutaverse.game.map.dto.response.PostMapUserResponse;

import kutaverse.game.map.repository.UserCashRepository;

import kutaverse.game.map.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("[Integration Test] -Map Controller Test")
public class UserControllerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UserCashRepository userCashRepository;

    User user1=new User("1",2.1,1.1,1.1,1.1,1.1,1.1,1.1,1.1,1.1, Status.JUMP,1,1);
    User user2=new User("2",1.1,1.1,1.1,1.1,1.1,1.1,2.2,2.2,2.2, Status.STAND,1,1);
    GetMapUserResponse userResponse1=GetMapUserResponse.toDto(user1);

    @PostConstruct
    public void initDB(){
        Mono<Void> saveUsers = userCashRepository.add(user1)
                .then(userCashRepository.add(user2))
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
                .expectBody(GetMapUserResponse.class)
                .isEqualTo(userResponse1);
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
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("post 요청으로 db에 저장할 수 있다.")
    public void test3(){
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).configureClient().responseTimeout(Duration.ofHours(1)).build();
        User user3=new User("3",1.234,2.345,3.4567,1.1,1.1,1.1,1.1,1.1,1.1, Status.NOTUSE,1,1);
        PostMapUserRequest postMapUserRequest=PostMapUserRequest.toEntity(user3);
        PostMapUserResponse postMapUserResponse=PostMapUserResponse.toDto(user3);
        webTestClient.post()
                .uri("/user")
                .body(Mono.just(postMapUserRequest),PostMapUserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostMapUserResponse.class)
                .isEqualTo(postMapUserResponse);

    }
}
