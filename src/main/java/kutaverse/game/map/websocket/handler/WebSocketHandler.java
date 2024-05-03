package kutaverse.game.map.websocket.handler;

import kutaverse.game.map.dto.UserRequestDto;

public interface WebSocketHandler {
    void handle(UserRequestDto userRequestDto);
}
