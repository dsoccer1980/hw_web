package ru.dsoccer1980;

import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.domain.Genre;

public class TestData {

    public static final Author AUTHOR1 = new Author("100000000000000000000001", "Стругацкий");
    public static final Author AUTHOR2 = new Author("100000000000000000000002", "Уэллс");
    public static final Author AUTHOR3 = new Author("100000000000000000000003", "Пушкин");
    public static final Author NEW_AUTHOR = new Author("100000000000000000000004", "Новый автор");
    public static final Genre GENRE1 = new Genre("100000000000000000000010", "Фантастика");
    public static final Genre GENRE2 = new Genre("100000000000000000000011", "Классика");
    public static final Genre NEW_GENRE = new Genre("100000000000000000000012", "Новый жанр");
    public static final Book BOOK1 = new Book("100000000000000000000100", "Трудно быть Богом", AUTHOR1, GENRE1);
    public static final Book BOOK2 = new Book("100000000000000000000101", "Машина времени", AUTHOR2, GENRE1);
    public static final Book BOOK3 = new Book("100000000000000000000102", "Онегин", AUTHOR3, GENRE2);
    public static final Book NEW_BOOK = new Book("100000000000000000000103", "Новая книга", AUTHOR3, GENRE2);
}
