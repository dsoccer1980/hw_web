package ru.dsoccer1980.util.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.dsoccer1980.domain.*;

import java.util.Set;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private static final Author AUTHOR1 = new Author("100000000000000000000001", "Стругацкий");
    private static final Author AUTHOR2 = new Author("100000000000000000000002", "Уэллс");
    private static final Author AUTHOR3 = new Author("100000000000000000000003", "Пушкин");
    private static final Genre GENRE1 = new Genre("100000000000000000000010", "Фантастика");
    private static final Genre GENRE2 = new Genre("100000000000000000000011", "Классика");
    private static final Book BOOK1 = new Book("100000000000000000000100", "Трудно быть Богом", AUTHOR1, GENRE1);
    private static final Book BOOK2 = new Book("100000000000000000000101", "Машина времени", AUTHOR2, GENRE1);
    private static final Book BOOK3 = new Book("100000000000000000000102", "Онегин", AUTHOR3, GENRE2);
    private static final User USER1 = new User(1L, "admin", new BCryptPasswordEncoder().encode("password"), Set.of(Role.ADMIN));
    private static final User USER2 = new User(2L, "user", new BCryptPasswordEncoder().encode("password"), Set.of(Role.USER));
    private Author springDataAuthor;
    private Genre springDataGenre;
    private Book springDataBook;
    private User springDataUser;

    @ChangeSet(order = "000", id = "dropDB", author = "stvort", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "stvort", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        springDataAuthor = template.save(AUTHOR1);
        springDataAuthor = template.save(AUTHOR2);
        springDataAuthor = template.save(AUTHOR3);
    }

    @ChangeSet(order = "002", id = "initGenres", author = "stvort", runAlways = true)
    public void initGenres(MongoTemplate template) {
        springDataGenre = template.save(GENRE1);
        springDataGenre = template.save(GENRE2);
    }

    @ChangeSet(order = "003", id = "initBooks", author = "stvort", runAlways = true)
    public void initBooks(MongoTemplate template) {
        springDataBook = template.save(BOOK1);
        springDataBook = template.save(BOOK2);
        springDataBook = template.save(BOOK3);
    }

    @ChangeSet(order = "004", id = "initUsers", author = "stvort", runAlways = true)
    public void initUsers(MongoTemplate template) {
        springDataUser = template.save(USER1);
        springDataUser = template.save(USER2);

    }
}