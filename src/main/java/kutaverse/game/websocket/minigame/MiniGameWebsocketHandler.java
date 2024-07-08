package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.minigame.dto.MiniGameRequest;
import kutaverse.game.minigame.dto.MiniGameRequestType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class MiniGameWebsocketHandler implements org.springframework.web.reactive.socket.WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(data -> {
                    try {
                        return objectMapper.readValue(data, MiniGameRequest.class);
                    } catch (Exception e) {
                        log.error("Error handling MiniGameRequest", e);
                        return null;
                    }
                })
                .subscribe(data -> {
                    try {
                        handleMiniGameRequest((MiniGameRequest) data,session);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
        return Mono.never();
    }

    /*
    * RequestType에 따라 다른 동작을 수행
    * WAIT -> 현재 대기 상태, 매칭 큐에 들어간다.
    * RUNNING -> 현재 게임 중인 상태, 자신의 상태(위치 값, 점수)를 상대방에게 보낸다.
    * OVER -> 게임이 끝난 상태, 상대방에게도 게임 종료를 알리고 서로의 점수를 기록한다.
    */

    private Mono<Void> handleMiniGameRequest(MiniGameRequest miniGameRequest,WebSocketSession session) throws JsonProcessingException {
        if (miniGameRequest.getMiniGameRequestType() == MiniGameRequestType.WAIT) {
            MatchingQueue.addPlayer(miniGameRequest.getUserId(), session);
        } else if (miniGameRequest.getMiniGameRequestType() == MiniGameRequestType.RUNNING) {
            GameRoomManager.updateGameRoom(miniGameRequest.getRoomId(), miniGameRequest);
            GameRoomManager.sendPlayerData(miniGameRequest.getRoomId(), miniGameRequest.getUserId(), miniGameRequest);
        } else {
            GameRoomManager.updateGameRoom(miniGameRequest.getRoomId(), miniGameRequest);

            // 게임 승패 판정 -> 게임 룸 클래스에 함수를 추가?
            GameRoomManager.endGameAndNotifyRoom(miniGameRequest);

            // 게임 룸 삭제
            GameRoomManager.removeGameRoom(miniGameRequest.getRoomId());
        }
        return Mono.never();
    }
}
