package kutaverse.game.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_chat_room")
public class UserChatRoom {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("chat_room_id")
    private Long chatRoomId;
}
