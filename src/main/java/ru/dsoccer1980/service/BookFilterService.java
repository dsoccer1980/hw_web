package ru.dsoccer1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsoccer1980.dto.BookDto;
import ru.dsoccer1980.util.config.YamlProps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class BookFilterService {

    private final YamlProps yamlProps;
    private final String FILENAME;
    private final String REPLACING_STRING;

    @Autowired
    public BookFilterService(YamlProps yamlProps) {
        this.yamlProps = yamlProps;
        FILENAME = URLDecoder.decode(yamlProps.getBannedWordsFilename(), StandardCharsets.UTF_8);
        REPLACING_STRING = yamlProps.getReplacingString();
    }

    public BookDto filter(BookDto book) {
        Set<String> bannedWords = getBannedWordsList();
        String lowerCaseBookName = book.getName().toLowerCase();
        bannedWords.stream()
                .filter(lowerCaseBookName::contains)
                .forEach(word -> book.setName(book.getName().replaceAll("(?i)" + Pattern.quote(word), REPLACING_STRING)));
        return book;
    }

    private Set<String> getBannedWordsList() {
        try (InputStream inputStream = BookFilterService.class.getClassLoader().getResourceAsStream(FILENAME)) {
            if (inputStream == null) {
                return Collections.emptySet();
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                Set<String> bannedWords = new HashSet<>();
                String word;
                while ((word = reader.readLine()) != null) {
                    bannedWords.add(word);
                }
                return bannedWords;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }
}
