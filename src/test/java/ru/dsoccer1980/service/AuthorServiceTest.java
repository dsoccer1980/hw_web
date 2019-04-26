package ru.dsoccer1980.service;

import com.github.cloudyrock.mongock.Mongock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dsoccer1980.domain.Author;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.dsoccer1980.TestData.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @Test
    void getAll() {
        List<Author> authors = authorService.getAll();
        assertThat(authors.toString()).isEqualTo(Arrays.asList(AUTHOR1, AUTHOR2, AUTHOR3).toString());
    }

    @Test
    void get() {
        Author author = authorService.get(AUTHOR1.getId());
        assertThat(author).isEqualTo(AUTHOR1);
    }

    @Test
    void addNewAuthor() {
        Author newAuthor = new Author("Новый автор");
        authorService.save(newAuthor);
        List<String> authors = authorService.getAll().stream().map(Author::getName).collect(Collectors.toList());
        assertThat(authors.toString()).isEqualTo(Arrays.asList(AUTHOR1.getName(), AUTHOR2.getName(), AUTHOR3.getName(), newAuthor.getName()).toString());
    }

    @Test
    void updateExistAuthor() {
        Author author = authorService.get(AUTHOR1.getId());
        author.setName("Новое имя");
        authorService.save(author);
        List<Author> authors = authorService.getAll();
        assertThat(authors.toString()).isEqualTo(Arrays.asList(author, AUTHOR2, AUTHOR3).toString());
    }

    @Test
    void delete() {
        authorService.delete(AUTHOR1.getId());
        List<Author> authors = authorService.getAll();
        assertThat(authors.toString()).isEqualTo(Arrays.asList(AUTHOR2, AUTHOR3).toString());
    }


}
