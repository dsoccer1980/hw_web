package ru.dsoccer1980.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.dsoccer1980.domain.Book;

@Data
@AllArgsConstructor
public class BookDto {

    private String id;
    private String name;
    private String authorId;
    private String authorName;
    private String genreId;

    public static Book getBook(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getName(), null, null);
    }

}
