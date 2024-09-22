package kutaverse.game.websocket.minigame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomAssignmentMessage {
    private String player1;
    private String player2;
    private String roomId;

    public RoomAssignmentMessage(String player1, String player2, String roomId) {
        this.player1 = player1;
        this.player2 = player2;
        this.roomId = roomId;
    }
}
