package pg.eti.kask.jee.quickr.chat.websocket;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import pg.eti.kask.jee.quickr.chat.model.ChatMessage;

/**
 * Encodes ChatMessage to JSON.
 */
public class MessageEncoder implements Encoder.Text<ChatMessage> {

    private Jsonb jsonb;

    @Override
    public void init(EndpointConfig config) {
        jsonb = JsonbBuilder.create();
    }

    @Override
    public String encode(ChatMessage object) throws EncodeException {
        if (jsonb == null) {
            jsonb = JsonbBuilder.create();
        }
        return jsonb.toJson(object);
    }

    @Override
    public void destroy() {
        if (jsonb != null) {
            try {
                jsonb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
