package kutaverse.game.map.unit;

import kutaverse.game.map.controller.RedisUserController;
import kutaverse.game.map.controller.UserController;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.ExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@Import(RedisUserController.class)
public class RedisUserControllerAPITest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("unit test-API test user를 저장했을 때 user에 대한 return 값을 받아 와야 한다. " +
            "작업이 필요합니다")
    public void test1(){
        //given
        User user=new User("1","user1");
        User user1=new User("1","user1");
        Mockito.when(userService.create(user)).thenReturn(Mono.just(user));
        //when

        webTestClient.post()
                .uri("/user")
                .body(Mono.just(user), User.class)
                .exchange()

                .expectStatus().isOk()
                .expectBody();


    }

    @Test
    @DisplayName("unit test-API test 저장된 유저를 찾아야한다.")
    public void test2(){
        //given
        String key="1";
        User user=new User(key,"user1");
        Mockito.when(userService.findOne("1")).thenReturn(Mono.just(user));
        //when

        webTestClient.get()
                .uri("/user/1")
                .exchange()

                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.key").isEqualTo(key)
                .jsonPath("$.name").isEqualTo("user1");

        Mockito.verify(userService,Mockito.times(1)).findOne(key);
    }

    @Test
    @DisplayName("unit test-API test 저장된 모든 유저를 찾을 수 있어야한다.")
    public void test3(){
        //given

        User user1=new User("1","user1");
        User user2=new User("2","user2");

        Mockito.when(userService.findAll()).thenReturn(Flux.just(user1,user2));
        //when

        webTestClient.get()
                .uri("/user")
                .exchange()

                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].key").isEqualTo("1")
                .jsonPath("$[0].name").isEqualTo("user1")
                .jsonPath("$[1].key").isEqualTo("2")
                .jsonPath("$[1].name").isEqualTo("user2");


        //then
        Mockito.verify(userService,Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("unit test-API test 유저를 삭제할 수 있어야한다.")
    public void test4(){
        //given
        String key="1";
        Long reval=1L;

        Mockito.when(userService.deleteById(key)).thenReturn(Mono.just(reval));
        //when

        webTestClient.delete()
                .uri("/user/1")
                .exchange()

                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("1");

        //then
        Mockito.verify(userService,Mockito.times(1)).deleteById(key);


    }
}