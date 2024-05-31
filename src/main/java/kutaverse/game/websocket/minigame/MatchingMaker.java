package kutaverse.game.websocket.minigame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Map;

@Component
@Slf4j
public class MatchingMaker {


    @Scheduled(fixedRate = 1000)
    public void matchPlayers(){
        log.info(String.valueOf(MatchingQueue.queueingSize()));
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
        GameRoom gameRoom = new GameRoom(roomId);

        gameRoom.addPlayer(player1.getKey(), player1.getValue());
        gameRoom.addPlayer(player2.getKey(), player2.getValue());

        GameRoomManager.addGameRoom(gameRoom);

        gameRoom.broadcastMessage(roomId);
    }
}
