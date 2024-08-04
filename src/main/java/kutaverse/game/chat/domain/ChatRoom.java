package kutaverse.game.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("chat_room")
public class ChatRoom {

    @Id
    private Long id;

    private LocalDateTime createdAt;
}
