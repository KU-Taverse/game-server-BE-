package kutaverse.game.map.websocket.handler;

import jakarta.annotation.PostConstruct;
import kutaverse.game.map.dto.MapRequestType;
import kutaverse.game.map.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
