package net.bromex.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
public class Utils {

    public static ObjectMapper mapper = new ObjectMapper();

    public static Object readJsonFromResource(final String resourcePath) {
        Object jsonObject = null;
        try {
            URL url = Utils.class.getResource(resourcePath);
            String json = Files.readString(Paths.get(url.toURI()));
            jsonObject = mapper.readTree(json);

        } catch (IOException | URISyntaxException e) {
            log.error("Failed to read the resource:"+resourcePath, e);
        }

        return jsonObject;
    }
}
