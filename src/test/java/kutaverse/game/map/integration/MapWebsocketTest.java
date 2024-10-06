package kutaverse.game.map.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.repository.UserCashRepository;
import kutaverse.game.map.repository.UserRepository;
import kutaverse.game.websocket.map.dto.response.UserResponseDto;
import kutaverse.game.websocket.map.util.JsonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.StandardWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=localDB")
@DisplayName("[Integration Test] -Map Websocket Test")
public class MapWebsocketTest {

    @LocalServerPort
    private String port;

    @Autowired
    UserCashRepository userCashRepository;

    User user1=new User("1",2.1,1.1,1.1,1.1,1.1,1.1,1.1,1.1,1.1, Status.JUMP,1,1);
    User user2=new User("2",1.1,1.1,1.1,1.1,1.1,1.1,2.2,2.2,2.2, Status.STAND,1,1);

    UserResponseDto userResponseDto1=UserResponseDto.toDto(user1);
    UserResponseDto userResponseDto2=UserResponseDto.toDto(user2);
    List<UserResponseDto> userResponseDtoList= List.of(userResponseDto1,userResponseDto2);

    @PostConstruct
    public void initDB() {
        Mono<Void> saveUsers = userCashRepository.add(user1)
                .then(userCashRepository.add(user2))
                .then();
        StepVerifier.create(saveUsers)
                .expectSubscription()
                .verifyComplete();
    }

    @DisplayName("저장된 map 유저 정보를 websocket로 받아야 한다.")
    @Test
    public void test1() throws URISyntaxException {
        int connectionTimeSecond=1;
        AtomicInteger counter = new AtomicInteger(0);

        WebSocketClient client = new ReactorNettyWebSocketClient();
        Exception exception = assertThrows(IllegalStateException.class, () -> client.execute(getUrl("/map"), session -> session.receive()
                .doOnNext(data -> {
                    List<UserResponseDto> userResponseDtos = JsonUtil.jsonToUserList(data.getPayloadAsText());
                    assertTrue(verifyResponse(userResponseDtos,userResponseDtoList));
                    counter.incrementAndGet();

                })
                .then()).block(Duration.ofSeconds(connectionTimeSecond)
        )
        );
        String expectedMessage = "Timeout on blocking read for 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("저장된 map 유저 정보중 NOTUSE 유저는 websocket로 받을 수 없다.")
    @Test
    public void test2() throws URISyntaxException {
        userCashRepository.delete(user1.getUserId()).block();
        userCashRepository.delete(user2.getUserId()).block();
        User notUseUser = new User("2", 1.1, 1.1, 1.1, 1.1, 1.1, 1.1,1.1,1.1,1.1, Status.NOTUSE,1,1);
        userCashRepository.add(notUseUser).block();

        int connectionTimeSecond=1;
        AtomicInteger counter = new AtomicInteger(0);


        WebSocketClient client = new ReactorNettyWebSocketClient();
        Exception exception = assertThrows(IllegalStateException.class, () -> client.execute(getUrl("/map"), session -> session.receive()
                .doOnNext(data -> {
                    Assertions.assertThat(JsonUtil.jsonToUserList(data.getPayloadAsText()).size()).isEqualTo(0);
                    counter.incrementAndGet();

                })
                .then()).block(Duration.ofSeconds(connectionTimeSecond)
        )
        );
        String expectedMessage = "Timeout on blocking read for 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private boolean verifyResponse(List<UserResponseDto> userResponseDtoList1, List<UserResponseDto> userResponseDtoList2) {
        int count=0;
        for (UserResponseDto userResponseDto1 : userResponseDtoList1) {
            for (UserResponseDto userResponseDto2 : userResponseDtoList2) {
                if(Objects.equals(userResponseDto1.getUserId(), userResponseDto2.getUserId())) {
                    count++;
                    break;
                }
            }
        }
        return count == userResponseDtoList1.size();
    }

    protected URI getUrl(String path) throws URISyntaxException {
        return new URI("ws://localhost:" + this.port + path);
    }
}
