package pg.eti.kask.jee.quickr.chat.websocket;

import jakarta.enterprise.context.Dependent;

import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.security.Principal;

/**
 * WebSocket Endpoint for Chat.
 * Observes ChatEvent to push messages to connected clients.
 */
@Dependent
@ServerEndpoint(value = "/chat", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class ChatEndpoint {

    @Inject
    private ChatSessionManager sessionManager;

    private Session session;
    private String username;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        Principal principal = session.getUserPrincipal();
        this.username = (principal != null) ? principal.getName() : "Anonymous";
        sessionManager.addSession(session, username);
    }

    @OnClose
    public void onClose() {
        sessionManager.removeSession(session);
        this.session = null;
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
