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

    private int userObjectNumber;

    public static TagGameMatchResponse toDto(String roomId, Map.Entry<String, WebSocketSession> tagger, int userObjectNumber ) {
        return TagGameMatchResponse.builder()
                .roomId(roomId)
                .taggerId(tagger.getKey())
                .userObjectNumber(userObjectNumber)
                .build();
    }

    @Override
    public String toString() {
        return "TagGameMatchResponse{" +
                "roomId='" + roomId + '\'' +
                ", taggerId='" + taggerId + '\'' +
                ", userObjectNumber=" + userObjectNumber +
                '}';
    }
}
