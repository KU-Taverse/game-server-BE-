package kutaverse.game.websocket.taggame.dto.request;

import kutaverse.game.websocket.taggame.dto.TagGameStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagGameRequest<T> {

    private TagGameStatus tagGameStatus;

    private T request;
}
