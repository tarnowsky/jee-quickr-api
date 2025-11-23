package pg.eti.kask.jee.quickr.chat.websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.websocket.Session;
import pg.eti.kask.jee.quickr.chat.event.ChatEvent;
import pg.eti.kask.jee.quickr.chat.model.ChatMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages active chat sessions and broadcasts messages.
 * This bean is ApplicationScoped, ensuring a single instance handles all
 * events.
 */
@ApplicationScoped
public class ChatSessionManager {

    // Map Session -> Username (or "Anonymous")
    private final Map<Session, String> sessions = new ConcurrentHashMap<>();

    public void addSession(Session session, String username) {
        sessions.put(session, username != null ? username : "Anonymous");
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /**
     * Observes ChatEvent and broadcasts to relevant sessions.
     * 
     * @param event The chat event.
     */
    public void onChatEvent(@Observes ChatEvent event) {
        ChatMessage msg = event.getMessage();

        sessions.forEach((session, username) -> {
            if (session.isOpen()) {
                boolean isRecipient = msg.getRecipient() == null
                        || "All".equals(msg.getRecipient())
                        || username.equals(msg.getRecipient())
                        || username.equals(msg.getSender());

                if (isRecipient) {
                    try {
                        session.getBasicRemote().sendObject(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
