package kutaverse.game.websocket.minigame.dto;

import kutaverse.game.minigame.domain.GameResult;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameResultDTO {
    private String player1Id;

    private String player2Id;

    private String roomId;

    public GameResult toEntity(){
        return GameResult.builder()
                .player1Id(player1Id)
                .player2Id(player2Id)
                .roomId(roomId)
                .build();
    }
}
