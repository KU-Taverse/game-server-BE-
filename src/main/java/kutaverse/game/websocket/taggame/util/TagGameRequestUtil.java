package kutaverse.game.websocket.taggame.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kutaverse.game.websocket.taggame.dto.TagGameRequest;
import kutaverse.game.websocket.taggame.dto.TagGameMatchRequest;
import kutaverse.game.websocket.taggame.dto.TagGameStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class TagGameRequestUtil {
    private static final Map<TagGameStatus, Class<?>> STATUS_TO_REQUEST_MAP =new HashMap<>();

    @PostConstruct
    private void putWebSocketHandler() {
        STATUS_TO_REQUEST_MAP.put(TagGameStatus.WAITING_FOR_PLAYERS, TagGameMatchRequest.class);
    }

    /**
     * websocket message를 적절한 TagGameRequest 값으로 제네릭 타입을 찾아 생성하여 반환한다.
     * @param message websocket message
     * @return tagGameRequest
     * @param <T>
     */
    public static <T> TagGameRequest<T> fromWebsocketMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(message);
            TagGameStatus status = TagGameStatus.valueOf(rootNode.get("tagGameStatus").asText());

            Class<T> clazz = (Class<T>) STATUS_TO_REQUEST_MAP.get(status);

            if (clazz == null) {
                throw new IllegalArgumentException("Unknown TagGameStatus");
            }
            T request = objectMapper.treeToValue(rootNode.get("request"), clazz);

            return new TagGameRequest<>(status, request);

        } catch (Exception e){
            throw new RuntimeException("parsing failed");
        }

    }
}
