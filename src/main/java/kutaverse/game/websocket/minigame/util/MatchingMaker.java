package kutaverse.game.websocket.minigame.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import kutaverse.game.minigame.service.MiniGameService;
import kutaverse.game.websocket.minigame.RoomService;
import kutaverse.game.websocket.minigame.dto.GameMatchingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchingMaker {

    private final MiniGameService miniGameService;
    private final RoomService roomService;

    @Scheduled(fixedRate = 1000)
    public void matchPlayers() throws JsonProcessingException {
        while(MatchingQueue.queueingSize() >= 2){
            Map.Entry<String, WebSocketSession> player1 = MatchingQueue.getPlayer();
            Map.Entry<String, WebSocketSession> player2 = MatchingQueue.getPlayer();

            // 세션 값 확인 필요
            if(player1.getValue().isOpen() && player2.getValue().isOpen()){
                createGameRoom(player1, player2);
                // 비동기적으로 세션을 닫음
                player1.getValue().close()
                        .doOnSuccess(aVoid -> log.info("Player 1 세션이 성공적으로 닫혔습니다."))
                        .doOnError(e -> log.error("Player 1 세션 닫기 실패", e))
                        .subscribe();

                player2.getValue().close()
                        .doOnSuccess(aVoid -> log.info("Player 2 세션이 성공적으로 닫혔습니다."))
                        .doOnError(e -> log.error("Player 2 세션 닫기 실패", e))
                        .subscribe();
            }
            else{
                if(player1.getValue().isOpen()){
                    MatchingQueue.requeue(player1);
                }
                if(player2.getValue().isOpen()){
                    MatchingQueue.requeue(player2);
                }
            }

        }
    }

    private void createGameRoom( Map.Entry<String, WebSocketSession> player1,  Map.Entry<String, WebSocketSession> player2) throws JsonProcessingException {
        String roomId = player1.getKey() + "-" + player2.getKey();
        GameRoom gameRoom = new GameRoom(roomId,miniGameService);

        gameRoom.addPlayer(player1.getKey(), player1.getValue());
        gameRoom.addPlayer(player2.getKey(), player2.getValue());

//        GameRoomManager.addGameRoom(gameRoom);

        String url = roomService.assignRoom(player1.getKey(),player2.getKey(),roomId);
        GameMatchingDTO gameMatchingDTO = new GameMatchingDTO(roomId,url);

        // 여기서 카프카 로직을 추가해서 넘겨주고 세션을 close 해야함
        gameRoom.broadcastMessage(String.valueOf(gameMatchingDTO));

    }
}