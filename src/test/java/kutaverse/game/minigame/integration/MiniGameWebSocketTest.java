package kutaverse.game.minigame.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.minigame.dto.ActorInfo;
import kutaverse.game.minigame.dto.MiniGameRequest;
import kutaverse.game.minigame.dto.MiniGameRequestType;
import kutaverse.game.websocket.minigame.util.GameRoom;
import kutaverse.game.websocket.minigame.util.GameRoomManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MiniGameWebSocketTest {
    @LocalServerPort
    private String port;

    @Test
    @DisplayName("Status == WAIT 이면 매칭 큐에 잡힌다.")
    public void testStatusWait() throws Exception {
        int connectionTimeSecond=2;
        WebSocketClient client = new ReactorNettyWebSocketClient();
        ObjectMapper objectMapper = new ObjectMapper();

        List<ActorInfo> actorInfoList = new ArrayList<>();
        ActorInfo actorInfo = new ActorInfo("1",0,0,0);
        actorInfoList.add(actorInfo);
        MiniGameRequest request = new MiniGameRequest(MiniGameRequestType.WAIT,"","test1",0,0,actorInfoList);

        String jsonRequest = objectMapper.writeValueAsString(request);
        Exception exception = assertThrows(IllegalStateException.class, () ->
                client.execute(getUrl("/game"), session -> {
                            WebSocketMessage message = session.textMessage(jsonRequest);

                            return session.send(Mono.just(message))
                                    .then(session.receive()
                                            .doOnNext(data -> {
                                                String serverResponse = data.getPayloadAsText();
                                                System.out.println(serverResponse);

                                                assertEquals("매칭 대기 중 입니다.", serverResponse);

                                            })
                                            .then()
                                    )
                                    .then();
                        })
                        .block(Duration.ofSeconds(connectionTimeSecond))
        );

        String expectedMessage = "Timeout on blocking read for 2";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Status == RUNNING 이면 상대방에게 나의 정보값이 넘어간다.")
    public void testStatusRunning() throws Exception {
        int connectionTimeSecond=2;
        WebSocketClient client = new ReactorNettyWebSocketClient();
        ObjectMapper objectMapper = new ObjectMapper();

        // User Setting
        List<ActorInfo> actorInfoList =new ArrayList<>();
        ActorInfo actorInfo = new ActorInfo("1",0,0,0);
        actorInfoList.add(actorInfo);
        MiniGameRequest request = new MiniGameRequest(MiniGameRequestType.RUNNING,"1","test1",0,0,actorInfoList);

        // GameRoom Setting
        WebSocketSession test1Session = createMockWebSocketSession("test1");
        WebSocketSession test2Session = createMockWebSocketSession("test2");

        String roomId = "1";
        GameRoom gameRoom = new GameRoom(roomId,null);

        gameRoom.addPlayer("test1", test1Session);
        gameRoom.addPlayer("test2", test2Session);

        GameRoomManager.addGameRoom(gameRoom);


        String jsonRequest = objectMapper.writeValueAsString(request);
        Exception exception = assertThrows(IllegalStateException.class, () ->
                client.execute(getUrl("/game"), session -> {
                            WebSocketMessage message = session.textMessage(jsonRequest);
                            return session.send(Mono.just(message))
                                    .then(session.receive()
                                            .doOnNext(data -> {
                                                String serverResponse = data.getPayloadAsText();
                                                System.out.println("Received response: " + serverResponse);
                                            })
                                            .then()
                                    );
                        })
                        .block(Duration.ofSeconds(connectionTimeSecond))
        );

        String expectedMessage = "Timeout on blocking read for 2";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Status == OVER 이면 종료 메시지가 넘어간다.")
    public void testStatusOver() throws Exception {

    }


    private URI getUrl(String path) throws URISyntaxException {
        return new URI("ws://localhost:" + port + path);
    }

    private WebSocketSession createMockWebSocketSession(String playerId) throws JsonProcessingException {
        WebSocketSession mockSession = Mockito.mock(WebSocketSession.class);

        Mockito.when(mockSession.getId()).thenReturn(playerId);

        Mockito.when(mockSession.close()).thenReturn(Mono.empty());

        ObjectMapper objectMapper = new ObjectMapper();
        List<ActorInfo> actorInfoList =new ArrayList<>();
        ActorInfo actorInfo = new ActorInfo("1",0,0,0);
        actorInfoList.add(actorInfo);
        MiniGameRequest request = new MiniGameRequest(MiniGameRequestType.RUNNING,"1","test1",0,0,actorInfoList);
        String dataJson = objectMapper.writeValueAsString(request);

        WebSocketMessage mockMessage = Mockito.mock(WebSocketMessage.class);
        Mockito.when(mockSession.send(Mockito.any())).thenReturn(Mono.just(mockMessage).then());

        Mockito.when(mockSession.textMessage(Mockito.anyString())).thenReturn(mockMessage);


        return mockSession;
    }
}

