package kutaverse.game.websocket.taggame.util;

import kutaverse.game.websocket.minigame.GameRoom;
import kutaverse.game.websocket.minigame.GameRoomManager;
import kutaverse.game.websocket.minigame.MatchingQueue;
import kutaverse.game.websocket.taggame.dto.TagGameMatchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class TagGameMatchingQueue {
    private static final ArrayDeque<Map.Entry<String, WebSocketSession>> queueing = new ArrayDeque<>();
    private static final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private static final Random random = new Random();
    public static void addPlayer(String userId, WebSocketSession webSocketSession){
        if(!sessionMap.containsKey(userId)) {
            queueing.offer(new AbstractMap.SimpleEntry<>(userId,webSocketSession));
            sessionMap.put(userId,webSocketSession);
            webSocketSession.send(Mono.just(webSocketSession.textMessage("매칭 대기 중 입니다."))).subscribe();
        }

    }

    private static Map.Entry<String, WebSocketSession> getPlayer(){
        return queueing.poll();
    }

    public static int getQueuePlayers(){
        return queueing.size();
    }

    private String createGameRoom(List<Map.Entry<String, WebSocketSession>> players) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, WebSocketSession> player : players) {
            stringBuilder.append(player.getKey());
            stringBuilder.append(" ");
        }
        String roomId = stringBuilder.toString();
        TagGameRoom tagGameRoom = new TagGameRoom(roomId,players);
        TagGameRoomManager.addGameRoom(tagGameRoom);
        return roomId;
    }

    @Scheduled(fixedRate = 1000)
    public void matchPlayers(){
        while(queueing.size() >= 4){
            Map.Entry<String, WebSocketSession> player1 = TagGameMatchingQueue.getPlayer();
            Map.Entry<String, WebSocketSession> player2 = TagGameMatchingQueue.getPlayer();
            Map.Entry<String, WebSocketSession> player3 = TagGameMatchingQueue.getPlayer();
            Map.Entry<String, WebSocketSession> player4 = TagGameMatchingQueue.getPlayer();

            // 세션 값 확인
            if(player1.getValue().isOpen() && player2.getValue().isOpen()
            && player3.getValue().isOpen() && player4.getValue().isOpen()){
                List<Map.Entry<String, WebSocketSession>> players = List.of(player1, player2, player3, player4);
                String gameRoom = createGameRoom(players);
                Map.Entry<String, WebSocketSession> tagger = selectTagger(players);

                //플레이어들에게 gamerood 공지
                for (Map.Entry<String, WebSocketSession> player : players) {
                    WebSocketSession webSocketSession = player.getValue();
                    TagGameMatchResponse tagGameMatchResponse = TagGameMatchResponse.toDto(gameRoom, tagger);

                    WebSocketMessage webSocketMessage = webSocketSession.textMessage(tagGameMatchResponse.toString());
                    webSocketSession.send(Mono.just(webSocketMessage)).subscribe();
                }



            }
            else{
                if(player1.getValue().isOpen()){
                    MatchingQueue.requeue(player1);
                }
                if(player2.getValue().isOpen()){
                    MatchingQueue.requeue(player2);
                }
                if(player3.getValue().isOpen()){
                    MatchingQueue.requeue(player3);
                }
                if(player4.getValue().isOpen()){
                    MatchingQueue.requeue(player4);
                }
            }

        }
    }

    /**
     * 술래를 정한다
     * @param players
     * @return
     */
    private Map.Entry<String, WebSocketSession> selectTagger(List<Map.Entry<String, WebSocketSession>> players) {

        int randomIndex = random.nextInt(players.size()); // 4는 상한값으로, 0~3 사이의 값이 생성됩니다.

        return players.get(randomIndex);
    }
}