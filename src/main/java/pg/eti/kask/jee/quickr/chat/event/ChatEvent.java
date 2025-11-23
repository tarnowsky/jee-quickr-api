package pg.eti.kask.jee.quickr.chat.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.eti.kask.jee.quickr.chat.model.ChatMessage;

/**
 * CDI Event for new chat messages.
 */
@Getter
@AllArgsConstructor
public class ChatEvent {

    private final ChatMessage message;

}
