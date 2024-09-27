package kutaverse.game.websocket.taggame.util;

import kutaverse.game.taggame.service.TagGameUserService;
import kutaverse.game.websocket.taggame.dto.response.TagGameMatchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class TagGameMatchingQueue {
    //매칭큐
    private static final ArrayDeque<Map.Entry<String, WebSocketSession>> queueing = new ArrayDeque<>();
    //동일 유저 동시 요청에 대한 저장 map
    private static final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private static final Random random = new Random();
    private final TagGameUserService tagGameUserService;

    public static void addPlayer(String userId, WebSocketSession webSocketSession) {
        //동일한 ID에 대한 요청이 세션 연결이 끊어진 상황인 경우 끊어진 요청을 제거한다.
        if (sessionMap.containsKey(userId)) {
            if (!sessionMap.get(userId).isOpen())
                sessionMap.remove(userId);
        }
        //매칭에 넣는다.
        if (!sessionMap.containsKey(userId)) {
            queueing.offer(new AbstractMap.SimpleEntry<>(userId, webSocketSession));
            sessionMap.put(userId, webSocketSession);
            webSocketSession.send(Mono.just(webSocketSession.textMessage("매칭 대기 중 입니다."))).subscribe();
        }

    }

    private static Map.Entry<String, WebSocketSession> getPlayer() {
        return queueing.poll();
    }

    public static int getQueuePlayers() {
        return queueing.size();
    }

    private void createGameRoom(String roomId, List<Map.Entry<String, WebSocketSession>> players) {
        TagGameRoom tagGameRoom = new TagGameRoom(roomId, players);
        TagGameRoomManager.addGameRoom(tagGameRoom);
    }

    private String confirmGameRoomName(List<Map.Entry<String, WebSocketSession>> players) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, WebSocketSession> player : players) {
            stringBuilder.append(player.getKey());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    @Scheduled(fixedRate = 1000)
    public void matchPlayers() throws InterruptedException {
        while (queueing.size() >= 4) {
            Map.Entry<String, WebSocketSession> player1 = TagGameMatchingQueue.getPlayer();
            Map.Entry<String, WebSocketSession> player2 = TagGameMatchingQueue.getPlayer();
            Map.Entry<String, WebSocketSession> player3 = TagGameMatchingQueue.getPlayer();
            Map.Entry<String, WebSocketSession> player4 = TagGameMatchingQueue.getPlayer();

            // 세션 값 확인
            if (player1.getValue().isOpen() && player2.getValue().isOpen()
                    && player3.getValue().isOpen() && player4.getValue().isOpen()) {
                List<Map.Entry<String, WebSocketSession>> players = List.of(player1, player2, player3, player4);

                Map.Entry<String, WebSocketSession> tagger = selectTagger(players);
                String gameRoom = confirmGameRoomName(players);
                //플레이어들에게 gameRoom 공지
                Integer counter = 1;
                List<Integer> integerList = new ArrayList<>();
                for (Map.Entry<String, WebSocketSession> player : players) {
                    WebSocketSession webSocketSession = player.getValue();
                    integerList.add(counter);
                    TagGameMatchResponse tagGameMatchResponse = TagGameMatchResponse.toDto(gameRoom, tagger, counter++);

                    WebSocketMessage webSocketMessage = webSocketSession.textMessage(tagGameMatchResponse.toString());
                    webSocketSession.send(Mono.just(webSocketMessage)).subscribe();
                }
                Thread.sleep(5000);
                tagGameUserService.initialize(players, tagger, integerList);
                createGameRoom(gameRoom, players);

            } else {
                if (player1.getValue().isOpen()) {
                    requeue(player1);
                }
                if (player2.getValue().isOpen()) {
                    requeue(player1);
                }
                if (player3.getValue().isOpen()) {
                    requeue(player1);
                }
                if (player4.getValue().isOpen()) {
                    requeue(player1);
                }
            }

        }
    }

    public static void requeue(Map.Entry<String, WebSocketSession> player) {
        queueing.addFirst(player);
    }

    /**
     * 술래를 정한다
     *
     * @param players
     * @return
     */
    private Map.Entry<String, WebSocketSession> selectTagger(List<Map.Entry<String, WebSocketSession>> players) {

        int randomIndex = random.nextInt(players.size()); // 4는 상한값으로, 0~3 사이의 값이 생성됩니다.

        return players.get(randomIndex);
    }

    /**
     * 연결이 끝어진 세션 회수 작업
     */
    @Scheduled(fixedRate = 10000)
    public void cleanSessionIfClosed() {
        queueing.removeIf(webSocketSessionEntry -> !webSocketSessionEntry.getValue().isOpen());
        for (Map.Entry<String, WebSocketSession> webSocketSessionEntry : sessionMap.entrySet()) {
            if(!webSocketSessionEntry.getValue().isOpen())
                sessionMap.remove(webSocketSessionEntry.getKey());
        }
    }
}