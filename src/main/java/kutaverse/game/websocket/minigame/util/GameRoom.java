package kutaverse.game.websocket.minigame.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.minigame.dto.MiniGameRequest;
import kutaverse.game.minigame.service.MiniGameService;
import kutaverse.game.websocket.minigame.dto.GameResultDTO;
import lombok.Getter;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/*
* 게임 방을 관리
* 해당 방에는 player들을 저장(해당 유저의 아이디, 접속한 websocket session)
* */
@Getter
public class GameRoom {
    private final String roomId;

    private final Map<String, WebSocketSession> players = new ConcurrentHashMap<>();
    private final Map<String, Integer> playerScores = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MiniGameService miniGameService;

    public GameRoom(String roomId, MiniGameService miniGameService) {
        this.roomId = roomId;
        this.miniGameService = miniGameService;
    }

    /**
     * 게임 방에 플레이어를 추가
     *
     * @param userId           플레이어의 ID
     * @param webSocketSession 플레이어의 WebSocket 세션
     */
    public void addPlayer(String userId, WebSocketSession webSocketSession){
        players.put(userId,webSocketSession);
        playerScores.put(userId,0);
    }


    public void removePlayer(String userId){
        players.remove(userId);
        playerScores.remove(userId);
    }

    // 이건 나중에 필요한 값들을 보내는 용도
    public void broadcastMessage(String message) {
        players.values().forEach(s ->{
            s.send(Mono.just(s.textMessage(message))).subscribe();
        });
    }

    public void sendDataToOther(String userId, MiniGameRequest data) throws JsonProcessingException {
        String dataJson = objectMapper.writeValueAsString(data);
        players.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(userId))
                .forEach(entry -> {
                    WebSocketSession session = entry.getValue();
                    session.send(Mono.just(session.textMessage(dataJson))).subscribe();
                });
    }

    /**
     * 특정 플레이어가 게임 방을 떠났을 때 다른 모든 플레이어에게 알림
     *
     * @param userId 게임 방을 떠난 플레이어의 ID
     */
    public void handlePlayerLeft(String userId){
        players.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(userId))
                .forEach(entry -> {
                    WebSocketSession session = entry.getValue();
                    session.send(Mono.just(session.textMessage("상대방이 나갔습니다."))).subscribe();
                });

        players.forEach((id, session) -> {
            players.remove(id);
        });
    }

    /**
     * 특정 플레이어의 점수를 업데이트
     *
     * @param userId 플레이어의 ID
     * @param score  업데이트할 점수
     */
    public void updatePlayerScore(String userId, int score) {
        playerScores.put(userId, score);
    }


    /**
     * 특정 플레이어의 현재 점수를 반환
     *
     * @param userId 플레이어의 ID
     * @return 플레이어의 현재 점수
     */
    public int getPlayerScore(String userId) {
        return playerScores.getOrDefault(userId, 0);
    }


    /**
     * 게임이 끝났음을 각 유저에게 알리고, 해당 게임의 데이터를 저장.
     *
     * @param miniGameRequest 저장할 게임 데이터
     */
    public void endGameAndNotifyPlayers(MiniGameRequest miniGameRequest) throws JsonProcessingException {
        // 어떤 형식으로 데이터를 보낼 것인가?
        // 유저1, 유저2, 점수1, 점수2, 누구 승

        GameResultDTO gameResultDTO = new GameResultDTO();

        Map.Entry<String, WebSocketSession> firstEntry = players.entrySet().iterator().next();
        gameResultDTO.setPlayer1Id(firstEntry.getKey());
        gameResultDTO.setPlayer1Score(getPlayerScore(firstEntry.getKey()));

        Map.Entry<String, WebSocketSession> secondEntry = players.entrySet().iterator().next();
        gameResultDTO.setPlayer2Id(secondEntry.getKey());
        gameResultDTO.setPlayer2Score(getPlayerScore(secondEntry.getKey()));

        String winnerId = miniGameRequest.getUserId().equals(firstEntry.getKey()) ? secondEntry.getKey() : firstEntry.getKey();
        gameResultDTO.setWinnerId(winnerId);

        gameResultDTO.setRoomId(miniGameRequest.getRoomId());

        String gameDataJson = objectMapper.writeValueAsString(gameResultDTO);

        // 각 유저에게 게임 종료 알림을 보낸다.
        players.values().forEach(session -> {
            session.send(Mono.just(session.textMessage("게임이 종료되었습니다. " + gameDataJson))).subscribe();
        });

        // 게임 데이터를 저장합니다.
        saveGameData(gameResultDTO);
    }



    /**
     * 게임 데이터를 저장
     *
     * @param gameResultDTO 저장할 게임 데이터
     */
    private void saveGameData(GameResultDTO gameResultDTO) {
        // 현재 서버 이슈로 잠시 해당 기능은 주석 처리 함
        // miniGameService.createGameResult(gameResultDTO).subscribe();

    }
}
