package kutaverse.game.websocket.taggame.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Map;

@AllArgsConstructor
@Builder
public class TagGameMatchResponse {

    private String roomId;

    private String taggerId;

    public static TagGameMatchResponse toDto(String roomId, Map.Entry<String, WebSocketSession> tagger) {
        return TagGameMatchResponse.builder()
                .roomId(roomId)
                .taggerId(tagger.getKey())
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "roomId='" + roomId + '\'' +
                ", taggerId='" + taggerId + '\'' +
                '}';
    }
}
