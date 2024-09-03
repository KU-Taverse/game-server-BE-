package kutaverse.game.websocket.taggame.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class TagGameMatchRequest {

    private String userId;

    private String text;
}
