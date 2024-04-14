package kutaverse.game.map.websocket.handler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class WebSocketHandlerMapping {


    private static WebSocketHandler saveHandler;
    private static WebSocketHandler responseHandler;

    public final SaveHandler saveHandlerBean;
    public final ResponseHandler responseHandlerBean;

    @PostConstruct
    private void initialize() {
        saveHandler = saveHandlerBean;
        responseHandler=responseHandlerBean;
    }
    public static WebSocketHandler getHandler(String message){
        return saveHandler;
    }
}
