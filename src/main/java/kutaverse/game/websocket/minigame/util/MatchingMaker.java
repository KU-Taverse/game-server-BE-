package kutaverse.game.websocket.minigame.util;

import kutaverse.game.minigame.service.MiniGameService;
import kutaverse.game.websocket.minigame.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchingMaker {

    private final MiniGameService miniGameService;
    private final RoomService roomService;

    @Scheduled(fixedRate = 1000)
    public void matchPlayers(){
        while(MatchingQueue.queueingSize() >= 2){
            Map.Entry<String, WebSocketSession> player1 = MatchingQueue.getPlayer();
            Map.Entry<String, WebSocketSession> player2 = MatchingQueue.getPlayer();

            // 세션 값 확인 필요
            if(player1.getValue().isOpen() && player2.getValue().isOpen()){
                createGameRoom(player1, player2);
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

    private void createGameRoom( Map.Entry<String, WebSocketSession> player1,  Map.Entry<String, WebSocketSession> player2){
        String roomId = player1.getKey() + "-" + player2.getKey();
        GameRoom gameRoom = new GameRoom(roomId,miniGameService);

//        gameRoom.addPlayer(player1.getKey(), player1.getValue());
//        gameRoom.addPlayer(player2.getKey(), player2.getValue());

//        GameRoomManager.addGameRoom(gameRoom);

        gameRoom.broadcastMessage(roomId);

        // 여기서 카프카 로직을 추가해서 넘겨주고 세션을 close 해야함
        gameRoom.broadcastMessage(roomService.assignRoom(player1.getKey(),player2.getKey(),roomId));

        try {
            player1.getValue().close();
            player2.getValue().close();
        } catch (Exception e) {
            log.error("WebSocket 세션 닫기 실패", e);
        }

    }
}
