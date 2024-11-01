package kutaverse.game.websocket.taggame.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TagGameEndResponse {

    private String message;

    public static TagGameEndResponse toDto(String message){
        return TagGameEndResponse.builder()
                .message(message)
                .build();
    }
}
