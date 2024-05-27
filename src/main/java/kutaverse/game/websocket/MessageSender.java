package kutaverse.game.websocket;

import kutaverse.game.map.service.UserService;
import kutaverse.game.websocket.util.JsonUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MessageSender {

    private final MapWebSocketHandler webSocketHandler;

    private final UserService userService;

    private Long durationTime; //맵 유지 기간 N 초에 대해서

    public MessageSender(MapWebSocketHandler webSocketHandler, UserService userService) {
        this.webSocketHandler = webSocketHandler;
        this.userService = userService;
        this.durationTime = 10L;
    }
    @Scheduled(fixedRate = 100) // 0.1초마다 실행
    public void sendMessageToClients() {

        userService.findAllByTime(durationTime)
                .collectList()
                .map(JsonUtil::userListToJson)
                .subscribe(this::sendUsersAsJsonToClients);
    }

    private Mono<Void> sendUsersAsJsonToClients(String user) {
        return webSocketHandler.sendMessageToAllClients("{"+user+"}");
    }

    public void setDurationTime(Long durationTime) {
        this.durationTime = durationTime;
    }
    public Long getDurationTime(){ return this.durationTime; }
}