package kutaverse.game.websocket.taggame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class TagGameTaggingRequest {

    //술래 id
    private String taggerId;
    //잡힌 플레이어 id
    private String taggedPlayerId;

}
