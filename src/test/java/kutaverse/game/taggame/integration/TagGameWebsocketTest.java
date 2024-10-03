package kutaverse.game.taggame.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kutaverse.game.websocket.taggame.dto.request.TagGameMatchRequest;
import kutaverse.game.websocket.taggame.dto.request.TagGameRequest;
import kutaverse.game.websocket.taggame.dto.TagGameStatus;
import kutaverse.game.websocket.taggame.util.TagGameMatchingQueue;
import org.assertj.core.api.Assertions;
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
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.profiles.active=localDB")
@DisplayName("[Integration Test] -TagGame Websocket Test")
public class TagGameWebsocketTest {

    @LocalServerPort
    private String port;

    ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void initDB() {
        for (int i = 0; i < 3; i++) {
            WebSocketSession session = Mockito.mock(WebSocketSession.class);
            String userId = "user" + i;

            // Mock WebSocketSession의 동작 설정
            Mockito.when(session.getId()).thenReturn(userId);

            WebSocketMessage mockMessage = Mockito.mock(WebSocketMessage.class);
            Mockito.when(session.textMessage(Mockito.anyString())).thenReturn(mockMessage);
            Mockito.when(session.send(Mockito.any())).thenReturn(Mono.just(mockMessage).then());

            // 세션 처리 로직 테스트
            TagGameMatchingQueue.addPlayer(userId, session);
        }
    }

    @DisplayName("initDB를 확인한다")
    @Test
    public void test1() {
        Assertions.assertThat(TagGameMatchingQueue.getQueuePlayers()).isEqualTo(3);
    }

    @DisplayName("4명이 매칭큐에 들어오면 매칭 된다")
    @Test
    public void test2() throws JsonProcessingException, URISyntaxException {
        int connectionTimeSecond = 3;
        AtomicInteger counter = new AtomicInteger(0);

        WebSocketClient client = new ReactorNettyWebSocketClient();
        TagGameMatchRequest tagGameMatchRequest = new TagGameMatchRequest("testUser1");
        TagGameRequest<TagGameMatchRequest> tagGameRequest = new TagGameRequest<>(TagGameStatus.TAG_GAME_WAITING_FOR_PLAYERS,tagGameMatchRequest);
        String jsonRequest = objectMapper.writeValueAsString(tagGameRequest);

        // WebSocket 연결 및 메시지 송수신
        client.execute(getUrl("/taggame"), session -> {
            WebSocketMessage message = session.textMessage(jsonRequest);

            // 메시지 전송 후 주기적으로 서버의 응답을 수신하는 로직
            return session.send(Mono.just(message))
                    .thenMany(session.receive()
                            .doOnNext(data -> {
                                String serverResponse = data.getPayloadAsText();
                                System.out.println("Received response: " + serverResponse);
                                System.out.println("TagGameMatchingQueue.getQueuePlayers() = " + TagGameMatchingQueue.getQueuePlayers());
                            })
                            .take(Duration.ofSeconds(connectionTimeSecond))  // 설정한 시간 동안 메시지 수신
                            .then())
                    .then();
        }).block(Duration.ofSeconds(connectionTimeSecond + 1));
    }
    private URI getUrl(String path) throws URISyntaxException {
        return new URI("ws://localhost:" + port + path);
    }

}
