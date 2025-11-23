package pg.eti.kask.jee.quickr.chat.websocket;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;
import pg.eti.kask.jee.quickr.chat.model.ChatMessage;

/**
 * Decodes JSON to ChatMessage.
 */
public class MessageDecoder implements Decoder.Text<ChatMessage> {

    private Jsonb jsonb;

    @Override
    public void init(EndpointConfig config) {
        jsonb = JsonbBuilder.create();
    }

    @Override
    public ChatMessage decode(String s) throws DecodeException {
        if (jsonb == null) {
            jsonb = JsonbBuilder.create();
        }
        return jsonb.fromJson(s, ChatMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
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
