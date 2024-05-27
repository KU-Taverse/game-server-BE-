package kutaverse.game.websocket.handler;

import kutaverse.game.map.dto.UserRequestDto;
import kutaverse.game.map.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteHandler implements WebSocketHandler {

    private final UserService userService;
    @Override
    public void handle(UserRequestDto userRequestDto) {
        userService.deleteById(userRequestDto.getUserId()).subscribe();
    }
}
