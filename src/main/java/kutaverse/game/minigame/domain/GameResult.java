package kutaverse.game.minigame.domain;


import kutaverse.game.websocket.minigame.dto.GameUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("game_results")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResult {
    @Id
    private Long id;

    @Column("player1_id")
    private String player1Id;

    @Column("player2_id")
    private String player2Id;

    @Column("player1_score")
    private int player1Score;

    @Column("player2_score")
    private int player2Score;

    @Column("winner_id")
    private String winnerId;

    @Column("room_id")
    private String roomId;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;


    public void update(GameUpdateDTO gameUpdateDTO){
        player1Score = gameUpdateDTO.getPlayer1Score();
        player2Score = gameUpdateDTO.getPlayer2Score();
        winnerId = gameUpdateDTO.getWinnerId();
    }
}
