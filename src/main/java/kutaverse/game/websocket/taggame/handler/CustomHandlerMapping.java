package kutaverse.game.websocket.taggame.handler;

import kutaverse.game.websocket.taggame.dto.TagGameStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomHandlerMapping {

    private static final Map<TagGameStatus, Class<? extends CustomHandler>> map=new HashMap<>();

    private final ApplicationContext applicationContext;

    public CustomHandlerMapping(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    static {
        map.put(TagGameStatus.TAG_GAME_WAITING_FOR_PLAYERS, TagGameMatchingHandler.class);
        map.put(TagGameStatus.TAG_GAME_PLAYING_MOVING, TagGameMovingHandler.class);
        map.put(TagGameStatus.TAG_GAME_TAGGING, TagGameMovingHandler.class);
    }
    public CustomHandler getHandler(TagGameStatus tagGameStatus) {
        Class<? extends CustomHandler> handlerClass = map.get(tagGameStatus);
        if (handlerClass != null) {
            return applicationContext.getBean(handlerClass); // Bean을 ApplicationContext에서 가져옴
        }
        return null;
    }

}
