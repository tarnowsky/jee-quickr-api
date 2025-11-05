package pg.eti.kask.jee.quickr.serialization.component;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.*;

/**
 * ALL RIGHTS RESERVED TO Michał Wójcik
 * @see <a href="https://url-shortener.me/8SO7">Link to SimpleRPG Code</a>
  */
@Log
public class CloningUtility {

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T clone(T object) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(writeObject(object).toByteArray());
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return (T) ois.readObject();
        }

    }

    private <T extends Serializable> ByteArrayOutputStream writeObject(T object) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(object);
            return os;
        }
    }

}
