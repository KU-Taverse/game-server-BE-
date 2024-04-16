package kutaverse.game.map.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.map.dto.UserRequestDto;
import kutaverse.game.map.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveHandler implements WebSocketHandler {

    private final UserService userService;
    @Override
    public String handle(String e) {
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            UserRequestDto mapRequestDto=objectMapper.readValue(e, UserRequestDto.class);
            userService.update(mapRequestDto).subscribe();

        } catch (
                JsonMappingException ex) {
            throw new RuntimeException("jsonMappring Exception 발생");
        } catch (
                JsonProcessingException ex) {
            throw new RuntimeException("jsonprocessingexception 발생");
        }
        return "";
    }
}
