package kutaverse.game.map.controller;

import kutaverse.game.websocket.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

    private final MessageSender messageSender;

    @PostMapping("/time")
    public Mono<Long> setDuration(@RequestParam ("time")long time) {
        messageSender.setDurationTime(time);
        return Mono.just(messageSender.getDurationTime());
    }


    @GetMapping("/time")
    public Mono<Long> getDuration() {

        return Mono.just(messageSender.getDurationTime());
    }

}
