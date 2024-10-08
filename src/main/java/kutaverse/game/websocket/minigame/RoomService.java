package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.client.GameRoomClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
@Slf4j
public class RoomService {

    private final RoundRobinAllocator roundRobinAllocator;
    private final int[] weight = {0,0,0};
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public RoomService(RoundRobinAllocator roundRobinAllocator,
                       KafkaTemplate<String, String> kafkaTemplate) {
        this.roundRobinAllocator = roundRobinAllocator;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public String assignRoom(String player1, String player2, String roomId) throws JsonProcessingException {
        String assignedRoomServer = roundRobinAllocator.getRoomServer();
        System.out.println("할당된 서버의 주소: " + assignedRoomServer);

        // 서버 주소에서 서비스 번호 추출
        String serverInfo = assignedRoomServer.split("/")[3]; // "ws://localhost:9000/dis-game-service-1/game"에서 "dis-game-service-1" 추출
        int serviceNumber = Integer.parseInt(serverInfo.split("-")[3]); // "1" 추출

        // 새로운 포트 생성
        int newPort = 9000 + serviceNumber; // 9000에 서비스 번호 더하기
        log.info(String.valueOf(newPort));


        // 메시지 객체 생성
        RoomAssignmentMessage message = new RoomAssignmentMessage(player1, player2, roomId);

        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            log.info(jsonMessage);
            kafkaTemplate.send(String.valueOf(newPort), jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return assignedRoomServer;
    }
}
