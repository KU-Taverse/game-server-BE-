package kutaverse.game.map.unit;

import kutaverse.game.map.controller.RedisUserController;
import kutaverse.game.map.controller.UserController;
import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import kutaverse.game.map.dto.response.GetMapUserResponse;
import kutaverse.game.map.dto.response.PostMapUserResponse;
import kutaverse.game.map.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@Import(RedisUserController.class)//현재에는 필요 없는 것 같다
@DisplayName("unit test-controller test")
public class RedisUserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    UserController userController;

    User user=new User("1", 1.1, 2.1, 3.1, 4.1,5.1,6.1,7.1,8.1,9.1, Status.STAND);
    PostMapUserRequest postMapUserRequest=PostMapUserRequest.toEntity(user);
    PostMapUserResponse postMapUserResponse=PostMapUserResponse.toDto(user);

    GetMapUserResponse getMapUserResponse=GetMapUserResponse.toDto(user);
    //validation이 필요하다
    @Test
    @DisplayName("user를 저장했을 때 user에 대한 return 값을 받아 와야 한다.")
    public void test1(){
        //given
        Mockito.when(userService.create(postMapUserRequest)).thenReturn(Mono.just(postMapUserResponse));
        //when

        PostMapUserResponse saveUser=userController.addUser(postMapUserRequest).block();
        //then
        Mockito.verify(userService,Mockito.times(1)).create(postMapUserRequest);
        Assertions.assertThat(postMapUserResponse).isEqualTo(saveUser);
    }

    @Test
    @DisplayName("저장된 유저를 찾아야한다.")
    public void test2(){
        //given
        String userId=user.getUserId();

        Mockito.when(userService.findOne("1")).thenReturn(Mono.just(getMapUserResponse));
        //when

        GetMapUserResponse findUser=userController.getUser("1").block();
        //then
        Mockito.verify(userService,Mockito.times(1)).findOne(userId);
        Assertions.assertThat(user.getUserId()).isEqualTo(findUser.getUserId());
        Assertions.assertThat(findUser).isEqualTo(getMapUserResponse);
    }

    @Test
    @DisplayName("저장된 모든 유저를 찾을 수 있어야한다.(크기만 확인하는 테스트이다)")
    public void test3(){
        //given

        User user1=new User("1", 1.1, 2.1, 3.1, 4.1,5.1,6.1,7.1,8.1,9.1, Status.STAND);
        User user2=new User("1", 1.1, 2.1, 3.1, 4.1,5.1,6.1,7.1,8.1,9.1, Status.STAND);
        GetMapUserResponse getMapUserResponse1=GetMapUserResponse.toDto(user1);
        GetMapUserResponse getMapUserResponse2=GetMapUserResponse.toDto(user2);

        Mockito.when(userService.findAll()).thenReturn(Flux.just(getMapUserResponse1,getMapUserResponse2));
        //when

        Flux<GetMapUserResponse> allUser = userController.getAllUser();

        //then
        Mockito.verify(userService,Mockito.times(1)).findAll();

        Assertions.assertThat(allUser.count().block()).isEqualTo(2L);
    }

    @Test
    @DisplayName("유저를 삭제할 수 있어야한다.")
    public void test4(){
        //given
        String userId="1";
        Long reval=1L;

        Mockito.when(userService.deleteById(userId)).thenReturn(Mono.just(reval));
        //when

        Long removeLong = userController.deleteUser(userId).block();
        Flux<GetMapUserResponse> allUser = userController.getAllUser();

        //then
        Mockito.verify(userService,Mockito.times(1)).deleteById(userId);

        Assertions.assertThat(removeLong).isEqualTo(reval);
    }
}
