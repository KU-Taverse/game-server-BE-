package kutaverse.game.websocket.map.handler;

import jakarta.annotation.PostConstruct;
import kutaverse.game.map.domain.MapRequestType;
import kutaverse.game.websocket.map.dto.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class WebSocketHandlerMapping {

    private static final Map<MapRequestType,WebSocketHandler> map=new HashMap<>();

    private final SaveHandler saveHandlerBean;
    private final DeleteHandler deleteHandlerBean;
    private final ResponseHandler responseHandlerBean;

    @PostConstruct
    private void putWebSocketHandler() {
        map.put(MapRequestType.SAVE,saveHandlerBean);
        map.put(MapRequestType.DELETE,deleteHandlerBean);
    }

    public static WebSocketHandler getHandler(UserRequestDto userRequestDto) {
        return map.get(userRequestDto.getMapRequestType());
    }
}
