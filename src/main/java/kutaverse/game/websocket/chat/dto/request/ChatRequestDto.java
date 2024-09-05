package kutaverse.game.websocket.chat.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChatRequestDto {

    private String userId;
    private String content;

}
