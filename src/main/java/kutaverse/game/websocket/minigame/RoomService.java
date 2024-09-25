package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.client.GameRoomClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
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
