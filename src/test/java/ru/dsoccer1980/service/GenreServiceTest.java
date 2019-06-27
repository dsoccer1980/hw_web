package ru.dsoccer1980.service;

import com.github.cloudyrock.mongock.Mongock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dsoccer1980.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.dsoccer1980.TestData.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @Test
    void getAll() {
        List<Genre> genres = genreService.getAll();
        assertThat(genres.toString()).isEqualTo(Arrays.asList(GENRE1, GENRE2).toString());
    }

    @Test
    void get() {
        Genre genre = genreService.get(GENRE1.getId());
        assertThat(genre).isEqualTo(GENRE1);
    }

    @Test
    void save() {
        genreService.save(NEW_GENRE);
        List<Genre> genres = genreService.getAll();
        assertThat(genres.toString()).isEqualTo(Arrays.asList(GENRE1, GENRE2, NEW_GENRE).toString());
    }

    @Test
    void addNewGenre() {
        Genre newGenre = new Genre("Новый жанр");
        genreService.save(newGenre);
        List<String> genres = genreService.getAll().stream().map(Genre::getName).collect(Collectors.toList());
        assertThat(genres.toString()).isEqualTo(Arrays.asList(GENRE1.getName(), GENRE2.getName(), newGenre.getName()).toString());
    }

    @Test
    void updateExistGenre() {
        Genre genre = genreService.get(GENRE1.getId());
        genre.setName("Новое имя");
        genreService.save(genre);
        List<Genre> genres = genreService.getAll();
        assertThat(genres.toString()).isEqualTo(Arrays.asList(genre, GENRE2).toString());
    }

    @Test
    void delete() {
        genreService.delete(GENRE1.getId());
        List<Genre> genres = genreService.getAll();
        assertThat(genres.toString()).isEqualTo(Arrays.asList(GENRE2).toString());
    }


}
