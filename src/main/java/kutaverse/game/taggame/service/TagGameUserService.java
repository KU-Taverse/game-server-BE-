package kutaverse.game.taggame.service;

import kutaverse.game.taggame.domain.Role;
import kutaverse.game.taggame.domain.TagGameUser;
import kutaverse.game.taggame.repository.TagGameUserRepository;
import kutaverse.game.websocket.taggame.dto.request.TagGameResultStatus;
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
        tagGameUserRepository.get(taggerId)
                .subscribe(TagGameUser::confirmTagged);
        return Mono.never();
    }

    public Mono<Void> initialize(List<Map.Entry<String, WebSocketSession>> players, Map.Entry<String, WebSocketSession> tagger,List<Integer> integerList) {
        int counter = 0;
        for (Map.Entry<String, WebSocketSession> player : players) {
            Role role = Role.PLAYER;
            if (player.getKey().equals(tagger.getKey()))
                role = Role.TAGGER;

            TagGameUser tagGameUser = TagGameUser.initTagGameUser(player.getKey(), role, integerList.get(counter), counter);
            counter++;
            tagGameUserRepository.add(tagGameUser).subscribe();
        }
        return Mono.never();
    }

    /**
     * 유저가 접속이 끊기는 이벤트 처리
     * @param userId 유저 id
     * @return void
     */
    public Mono<Void> closedUser(String userId) {
        return tagGameUserRepository
                .get(userId)
                .flatMap(tagGameUser -> {
                    if(tagGameUser.getRole() == Role.TAGGER) {
                        tagGameUser.confirmTaggerOut();
                        return Mono.never();
                    }
                    tagGameUser.confirmTagged();
                    return Mono.never();
                });
    }

}
