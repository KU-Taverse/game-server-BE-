package kutaverse.game.chat.repository;

import kutaverse.game.chat.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<Chat,String> {
}
