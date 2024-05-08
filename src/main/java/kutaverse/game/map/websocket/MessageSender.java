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
public class MessageSender {

    private final CustomWebSocketHandler webSocketHandler;

    private final UserService userService;

    private Long durationTime; //맵 유지 기간 N 초에 대해서

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MessageSender(CustomWebSocketHandler webSocketHandler, UserService userService) {
        this.webSocketHandler = webSocketHandler;
        this.userService = userService;
        this.durationTime = 10L;
    }
    @Scheduled(fixedRate = 100) // 0.1초마다 실행
    public void sendMessageToClients() {

        userService.findAllByTime(durationTime)
                .collectList()
                .map(users -> {
                    try {
                        String json = objectMapper.writeValueAsString(users.stream()
                                .map(UserResponseDto::new)
                                .collect(Collectors.toList()));
                        if (json.startsWith("[") && json.endsWith("]")) {
                            json = json.substring(1, json.length() - 1);
                        }

                        return json;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace(); // 또는 예외를 처리하는 방법을 선택하세요.
                        return ""; // 또는 예외를 던지거나 다른 기본값을 반환하세요.
                    }
                })
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