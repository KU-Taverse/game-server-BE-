package kutaverse.game.websocket.minigame.dto;

import kutaverse.game.minigame.domain.GameResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GameResultDTO {
    private String player1Id;

    private String player2Id;

    private int player1Score;

    private int player2Score;

    private String winnerId;

    private String roomId;

    public GameResult toEntity(){
        return GameResult.builder()
                .player1Id(player1Id)
                .player2Id(player2Id)
                .roomId(roomId)
                .player1Score(player1Score)
                .player2Score(player2Score)
                .winnerId(winnerId)
                .build();
    }
}
