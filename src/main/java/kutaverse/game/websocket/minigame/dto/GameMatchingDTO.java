package kutaverse.game.websocket.minigame.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GameMatchingDTO {
    private String roomId;
    private String url;

    @Override
    public String toString() {
        return "{" +
                "\"roomId\":\"" + roomId + "\"" +
                ", \"url\":\"" + url + "\"" +
                '}';
    }
}
