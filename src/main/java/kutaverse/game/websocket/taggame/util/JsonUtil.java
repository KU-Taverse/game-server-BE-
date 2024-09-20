package kutaverse.game.websocket.taggame.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.taggame.domain.TagGameUser;
import kutaverse.game.websocket.taggame.dto.request.TagGameUserRequest;

import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param userList
     * @return userlist의 json 형태 ([]를 제거한 상태)
     */
    public static String userListToJson(List<TagGameUser> userList) {
        try {
            return objectMapper.writeValueAsString(userList.stream()
                            .map(TagGameUserRequest::toDto)
                            .collect(Collectors.toList()));

        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 또는 예외를 처리하는 방법을 선택하세요.
            throw new RuntimeException("직렬화중 문제 발생"); // 또는 예외를 던지거나 다른 기본값을 반환하세요.
        }
    }
    // 역직렬화
    public static List<TagGameUserRequest> jsonToUserList(String json) {
        try {
            List<TagGameUserRequest> tagGameUserResponseList = objectMapper.readValue(json, new TypeReference<>() {
            });
            return tagGameUserResponseList;

        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 또는 예외를 처리하는 방법을 선택하세요.
            throw new RuntimeException("역직렬화 중 문제 발생"); // 또는 예외를 던지거나 다른 기본값을 반환하세요.
        }
    }
    // UserResponseDto를 User로 변환하는 메서드



}
