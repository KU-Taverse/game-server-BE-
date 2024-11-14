package kutaverse.game.map.unit;

import kutaverse.game.map.controller.UserController;
import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import kutaverse.game.map.dto.response.GetMapUserResponse;
import kutaverse.game.map.dto.response.PostMapUserResponse;
import kutaverse.game.map.service.UserCashService;
import kutaverse.game.map.service.UserService;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.refEq;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@DisplayName("[Unit Test] -API Test")
public class UserControllerImplAPITest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UserController userController;

    @MockBean
    UserCashService userCashService;

    @MockBean
    UserService userService;


    User user = new User("1", 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 7.1, 8.1, 9.1, Status.STAND,1,1,1);
    User user1 = new User("1", 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 7.1, 8.1, 9.1, Status.STAND,1,1,1);
    User user2 = new User("2", 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 7.1, 8.1, 9.1, Status.STAND,1,1,1);
    PostMapUserRequest postMapUserRequest = PostMapUserRequest.toEntity(user);
    PostMapUserResponse postMapUserResponse = PostMapUserResponse.toDto(user);
    GetMapUserResponse getMapUserResponse = GetMapUserResponse.toDto(user);
    GetMapUserResponse getMapUserResponse1 = GetMapUserResponse.toDto(user1);
    GetMapUserResponse getMapUserResponse2 = GetMapUserResponse.toDto(user2);

    /**
     * TODO
     * 문제가 있다. 단위 테스트에서 Webclient가 문제 있다
     */
    @Test
    @DisplayName("user를 저장했을 때 user에 대한 return 값을 받아 와야 한다. " +
            "작업이 필요합니다")
    public void test1() {

        //given
        Mockito.when(userCashService.create(postMapUserRequest)).thenReturn(Mono.just(postMapUserResponse));
        //when
        webTestClient.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(postMapUserRequest), PostMapUserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserCashService.class);
        //then
        Mockito.verify(userCashService, Mockito.times(1)).create(refEq(postMapUserRequest));
    }

    @Test
    @DisplayName("저장된 유저를 찾아야한다.")
    public void test2() {

        //given
        String userId = getMapUserResponse.getUserId();
        Mockito.when(userService.findOne(userId)).thenReturn(Mono.just(getMapUserResponse));
        //when
        webTestClient.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.userId").isEqualTo(userId);
        //then
        Mockito.verify(userService, Mockito.times(1)).findOne(userId);
    }

    @Test
    @DisplayName("저장된 모든 유저를 찾을 수 있어야한다.")
    public void test3() {

        //given
        Mockito.when(userCashService.findAll()).thenReturn(Flux.just(getMapUserResponse1, getMapUserResponse2));
        //when
        webTestClient.get()
                .uri("/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].userId").isEqualTo("1")
                .jsonPath("$[1].userId").isEqualTo("2");
        //then
        Mockito.verify(userCashService, Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("유저를 삭제할 수 있어야한다.")
    public void test4() {

        //given
        String userId = "1";
        Long reval = 1L;
        Mockito.when(userCashService.deleteById(userId)).thenReturn(Mono.just(reval));
        //when
        webTestClient.delete()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("1");
        //then
        Mockito.verify(userCashService, Mockito.times(1)).deleteById(userId);
    }
}
