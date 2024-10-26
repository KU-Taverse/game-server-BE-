package kutaverse.game.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.WithBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collation = "chat")
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Chat {

    @Id
    private String id;
    private String nickname;
    private String senderUserId;
    private String content;
    private LocalDateTime createdAt;

}
