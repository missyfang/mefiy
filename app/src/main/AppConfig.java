package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private final Properties properties = new Properties();

    public AppConfig() throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        }
    }

    public String getClientId() {
        return properties.getProperty("client_id");
    }

    public String getRedirectUri() {
        return properties.getProperty("redirect_uri");
    }
}
