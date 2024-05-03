package kutaverse.game.map.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.UserRequestDto;
import kutaverse.game.map.dto.UserResponseDto;
import kutaverse.game.map.service.UserService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageSender {

    private final CustomWebSocketHandler webSocketHandler;

    private final UserService userService;

    ObjectMapper objectMapper=new ObjectMapper();

    @Scheduled(fixedRate = 1000) // 5초마다 실행
    public void sendMessageToClients() {

        userService.findAll()
                .collectList()
                .map(u -> u.stream()
                        .map(UserResponseDto::new)
                        .map(UserResponseDto::toString)
                        .collect(Collectors.joining(" ")))
                .subscribe(this::sendUsersAsJsonToClients);

    }

    private Mono<Void> sendUsersAsJsonToClients(String user) {
        return webSocketHandler.sendMessageToAllClients("{"+user+"}");
    }
}