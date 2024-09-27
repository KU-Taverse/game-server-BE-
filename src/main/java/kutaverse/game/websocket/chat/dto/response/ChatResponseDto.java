package kutaverse.game.websocket.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ChatResponseDto {

    private String userId;
    private String nickName;
    private String content;
}
