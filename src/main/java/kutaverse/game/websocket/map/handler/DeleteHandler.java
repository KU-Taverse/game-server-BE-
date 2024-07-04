package kutaverse.game.websocket.map.handler;

import kutaverse.game.websocket.map.dto.request.UserRequestDto;
import kutaverse.game.map.service.UserCashService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteHandler implements WebSocketHandler {

    private final UserCashService userService;
    @Override
    public void handle(UserRequestDto userRequestDto) {
        userService.deleteById(userRequestDto.getUserId()).subscribe();
    }
}
