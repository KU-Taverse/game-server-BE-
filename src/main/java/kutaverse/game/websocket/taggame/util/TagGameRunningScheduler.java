package kutaverse.game.websocket.taggame.util;

import kutaverse.game.taggame.domain.TagGameUser;
import kutaverse.game.taggame.repository.TagGameUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TagGameRunningScheduler {

    private final TagGameUserRepository tagGameUserRepository;
    /**
     * 멀티게임 이동 함수
     */
    @Scheduled(fixedRate = 3300)
    public void sendMessageToClients() {
        TagGameRoomManager.gameRooms.values()
                .forEach(tagGameRoom -> {
                    Flux.fromIterable(tagGameRoom.getPlayers())
                            .flatMap(player -> tagGameUserRepository.get(player.getKey()))
                            .collectList()
                            .map(JsonUtil::userListToJson)
                            .flatMapMany(json -> Flux.fromIterable(tagGameRoom.getPlayers())
                                    .flatMap(player -> {
                                        WebSocketMessage webSocketMessage = player.getValue().textMessage(json);
                                        return player.getValue().send(Mono.just(webSocketMessage));
                                    }))
                            .then() // 모든 작업이 완료될 때까지 대기
                            .subscribe();// JSON 변환

    }
}
