package ru.dsoccer1980.util.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "props")
public class YamlProps {

    private String bannedWordsFilename;
    private String replacingString;

    public String getBannedWordsFilename() {
        return bannedWordsFilename;
    }

    public void setBannedWordsFilename(String bannedWordsFilename) {
        this.bannedWordsFilename = bannedWordsFilename;
    }

    public String getReplacingString() {
        return replacingString;
    }

    public void setReplacingString(String replacingString) {
        this.replacingString = replacingString;
    }
}
