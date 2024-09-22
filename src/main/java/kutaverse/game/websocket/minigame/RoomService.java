package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoomService {

    private final RoundRobinAllocator roundRobinAllocator;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public RoomService(KafkaTemplate<String, String> kafkaTemplate) {
        this.roundRobinAllocator = new RoundRobinAllocator(Arrays.asList(
                "ws://localhost:9001/game-service/game",
                "ws://localhost:9002/game-service/game",
                "ws://localhost:9003/game-service/game"
        ));
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public String assignRoom(String player1, String player2, String roomId) {
        String assignedRoomServer = roundRobinAllocator.getRoomServer();
        System.out.println("할당된 서버의 주소: " + assignedRoomServer);

        // 서버 주소에서 토픽 추출
        String topic = assignedRoomServer.split(":")[2].split("/")[0]; // "ws://localhost:9001/game-service/game"에서 "9001" 추출

        // 메시지 객체 생성
        RoomAssignmentMessage message = new RoomAssignmentMessage(player1, player2, roomId);

        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(topic, jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return assignedRoomServer;
    }
}
