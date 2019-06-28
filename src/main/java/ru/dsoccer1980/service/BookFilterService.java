package ru.dsoccer1980.service;

import org.springframework.stereotype.Service;
import ru.dsoccer1980.domain.Book;

import java.util.List;

@Service
public class BookFilterService {

    private final List<String> bannedWords = List.of(
            "shit",
            "nude",
            "porno"
    );
    private final String REPLACING_STRING = "XXX";

    public Book filter(Book book) {
        String bookName = book.getName().toLowerCase();
        bannedWords.stream()
                .filter(bookName::contains)
                .forEach(word -> book.setName(bookName.replaceAll(word, REPLACING_STRING)));
        return book;
    }
}
