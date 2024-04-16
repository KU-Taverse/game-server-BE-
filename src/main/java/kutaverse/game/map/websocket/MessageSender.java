package kutaverse.game.map.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.service.UserService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MessageSender {

    private final CustomWebSocketHandler webSocketHandler;

    private final UserService userService;

    ObjectMapper objectMapper=new ObjectMapper();

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void sendMessageToClients() {

        userService.findAll().subscribe(u->sendUsersAsJsonToClients(u));
    }

    private Mono<Void> sendUsersAsJsonToClients(User user) {
        try {
            String json = objectMapper.writeValueAsString(user);
            return webSocketHandler.sendMessageToAllClients(json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}