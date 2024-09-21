package kutaverse.game.websocket.taggame.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagGameEndRequest {

    private String roomId;

    private TagGameResultStatus tagGameResultStatus;
}
