package kutaverse.game.taggame.service;

import kutaverse.game.taggame.domain.Role;
import kutaverse.game.taggame.domain.TagGameUser;
import kutaverse.game.taggame.repository.TagGameUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagGameUserService {

    private final TagGameUserRepository tagGameUserRepository;

    public Mono<Void> taggingUser(String roomName, String taggerId, String taggedPlayerId) {
        //tagGameUserRepository.
        return Mono.never();
    }

    public Mono<Void> initialize(List<Map.Entry<String, WebSocketSession>> players, Map.Entry<String, WebSocketSession> tagger) {
        players.forEach(player -> {
            Role role = Role.PLAYER;

            if (player.getKey().equals(tagger.getKey()))
                role = Role.TAGGER;

            TagGameUser tagGameUser = TagGameUser.initTagGameUser(player.getKey(),role);
            tagGameUserRepository.add(tagGameUser).subscribe();
        });
        return Mono.never();
    }
}
