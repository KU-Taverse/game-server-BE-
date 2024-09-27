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
    @Scheduled(fixedRate = 33)
    public void sendMessageToClients() {
        TagGameRoomManager.gameRooms.values()
                .forEach(tagGameRoom -> {

                    // 1. 플레이어 정보를 조회하고 리스트로 변환
                    Flux<TagGameUser> usersFlux = Flux.fromIterable(tagGameRoom.getPlayers().entrySet())
                            .flatMap(player -> tagGameUserRepository.get(player.getKey()));

                    // 2. 유저 정보를 JSON으로 변환
                    Mono<String> usersJsonMono = usersFlux.collectList()
                            .map(JsonUtil::userListToJson);

                    // 3. 각 플레이어에게 WebSocket 메시지 전송
                    usersJsonMono.flatMapMany(json -> sendMessagesToPlayers(tagGameRoom, json))
                            .then()  // 모든 메시지 전송 완료 대기
                            .subscribe();  // 비동기 작업 시작
                });
    }

    /**
     * 플레이어들에게 WebSocket 메시지 전송
     */
    private Flux<Void> sendMessagesToPlayers(TagGameRoom tagGameRoom, String json) {
        return Flux.fromIterable(tagGameRoom.getPlayers().entrySet())
                .flatMap(player -> {
                    if(!player.getValue().isOpen())
                        return Mono.never();
                    WebSocketMessage webSocketMessage = player.getValue().textMessage(json);
                    return player.getValue().send(Mono.just(webSocketMessage));
                });
    }
}
