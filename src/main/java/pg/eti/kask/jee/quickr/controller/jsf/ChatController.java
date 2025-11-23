package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.enterprise.event.Event;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter;
import lombok.Setter;
import pg.eti.kask.jee.quickr.chat.event.ChatEvent;
import pg.eti.kask.jee.quickr.chat.model.ChatMessage;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * JSF Controller for Chat.
 * Handles sending messages via CDI events.
 */
@ViewScoped
@Named
public class ChatController implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private Event<ChatEvent> chatEvent;

    @Getter
    @Setter
    private String messageContent;

    @Getter
    @Setter
    private String recipient = "All";

    /**
     * Sends a message by firing a CDI event.
     */
    public void sendMessage() {
        if (messageContent != null && !messageContent.trim().isEmpty()) {
            String sender = (securityContext.getCallerPrincipal() != null)
                    ? securityContext.getCallerPrincipal().getName()
                    : "Anonymous";

            ChatMessage msg = ChatMessage.builder()
                    .content(messageContent)
                    .sender(sender)
                    .recipient("All".equals(recipient) ? "All" : recipient)
                    .timestamp(LocalDateTime.now())
                    .build();

            chatEvent.fire(new ChatEvent(msg));
            messageContent = ""; // Clear input after sending
        }
    }

    public String getCurrentUser() {
        return (securityContext.getCallerPrincipal() != null)
                ? securityContext.getCallerPrincipal().getName()
                : "";
    }
}
