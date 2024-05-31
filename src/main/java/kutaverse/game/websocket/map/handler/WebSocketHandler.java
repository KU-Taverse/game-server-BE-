package kutaverse.game.websocket.map.handler;

import kutaverse.game.websocket.map.dto.request.UserRequestDto;

public interface WebSocketHandler {
    void handle(UserRequestDto userRequestDto);
}
