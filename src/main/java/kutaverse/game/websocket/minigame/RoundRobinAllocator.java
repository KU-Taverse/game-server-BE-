package kutaverse.game.websocket.minigame;

import java.util.List;

public class RoundRobinAllocator {
    private final List<String> roomServers;
    private int currentIndex = 0;

    public RoundRobinAllocator(List<String> roomServers) {
        this.roomServers = roomServers;
    }

    public synchronized String getRoomServer() {
        String server = roomServers.get(currentIndex);
        currentIndex = (currentIndex + 1) % roomServers.size();
        return server;
    }
}

