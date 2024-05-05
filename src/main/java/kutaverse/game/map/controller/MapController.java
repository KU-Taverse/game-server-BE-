package kutaverse.game.map.controller;

import kutaverse.game.map.domain.User;
import kutaverse.game.map.websocket.MessageSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

    private final MessageSender messageSender;

    @PostMapping("/time")
    public Mono<String> setDuration(@RequestParam ("time")long time) {
        messageSender.setDurationTime(time);
        return Mono.just("success");
    }


    @GetMapping("/time")
    public Mono<Long> getDuration() {

        return Mono.just(messageSender.getDurationTime());
    }

}
