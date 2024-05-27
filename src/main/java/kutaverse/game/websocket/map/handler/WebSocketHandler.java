package kutaverse.game.websocket.map.handler;

import kutaverse.game.map.dto.UserRequestDto;

public interface WebSocketHandler {
    void handle(UserRequestDto userRequestDto);
}
