package org.jboss.performance.parser.json;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by johara on 05/05/16.
 */
public class JsonFileReader {

    public static JsonObject readJson(URL srcUrl) {
        JsonObject obj = null;
        try (InputStream is = srcUrl.openStream();
             javax.json.JsonReader rdr = Json.createReader(is)) {

            obj = rdr.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
