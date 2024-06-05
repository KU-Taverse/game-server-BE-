package kutaverse.game.websocket.map.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.map.domain.User;
import kutaverse.game.websocket.map.dto.response.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param userList
     * @return userlist의 json 형태 ([]를 제거한 상태)
     */
    public static String userListToJson(List<User> userList) {
        try {
            return removeBrace(
                    objectMapper.writeValueAsString(userList.stream()
                            .map(UserResponseDto::toDto)
                            .collect(Collectors.toList()))
            );

        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 또는 예외를 처리하는 방법을 선택하세요.
            throw new RuntimeException("직렬화중 문제 발생"); // 또는 예외를 던지거나 다른 기본값을 반환하세요.
        }
    }
    // 역직렬화
    public static List<UserResponseDto> jsonToUserList(String json) {
        try {
            List<UserResponseDto> userResponseDtoList = objectMapper.readValue(addBrace(json), new TypeReference<>() {
            });
            return userResponseDtoList;

        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 또는 예외를 처리하는 방법을 선택하세요.
            throw new RuntimeException("역직렬화 중 문제 발생"); // 또는 예외를 던지거나 다른 기본값을 반환하세요.
        }
    }
    // UserResponseDto를 User로 변환하는 메서드


    /**
     *
     * @param str list를 json의 변환한 string
     * @return 시작과 끝 "[" "]"제거
     */
    public static String removeBrace(String str) {
        if (str.startsWith("[") && str.endsWith("]")) {
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }
    public static String addBrace(String str) {
        if (str.startsWith("{") && str.endsWith("}")) {
            str = str.substring(1, str.length() - 1);
        }

        return "["+str+"]";
    }
}
