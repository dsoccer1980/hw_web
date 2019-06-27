package ru.dsoccer1980.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.dsoccer1980.TestData.*;


public class GenreRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void populateData() {
        genreRepository.deleteAll();
        genreRepository.save(GENRE1);
        genreRepository.save(GENRE2);
    }

    @Test
    void getAll() {
        assertThat(genreRepository.findAll().toString()).isEqualTo(Arrays.asList(GENRE1, GENRE2).toString());
    }

    @Test
    void getById() {
        Genre genre = genreRepository.findById(GENRE1.getId()).orElseThrow(() -> new NotFoundException("Genre not found"));
        assertThat(genre.getId()).isEqualTo(GENRE1.getId());
    }

    @Test
    void getByWrongId() {
        assertThrows(NotFoundException.class, () -> genreRepository.findById("-1").orElseThrow(() -> new NotFoundException("Genre not found")));
    }

    @Test
    void insert() {
        int sizeBeforeInsert = genreRepository.findAll().size();
        Genre insertedGenre = genreRepository.save(NEW_GENRE);
        assertThat(insertedGenre.getName()).isEqualTo(NEW_GENRE.getName());
        assertThat(genreRepository.findAll().size()).isEqualTo(sizeBeforeInsert + 1);
    }

    @Test
    void insertExistGenre() {
        int sizeBeforeInsert = genreRepository.findAll().size();
        Genre insertedGenre = genreRepository.save(GENRE1);
        assertThat(insertedGenre.getName()).isEqualTo(GENRE1.getName());
        assertThat(genreRepository.findAll().size()).isEqualTo(sizeBeforeInsert);
    }

    @Test
    void deleteById() {
        int sizeBeforeDelete = genreRepository.findAll().size();
        genreRepository.deleteById(GENRE1.getId());
        assertThat(genreRepository.findAll().size()).isEqualTo(sizeBeforeDelete - 1);
    }

    @Test
    void deleteByWrongId() {
        int sizeBeforeDelete = genreRepository.findAll().size();
        genreRepository.deleteById("123456789012345678901234");
        assertThat(genreRepository.findAll().size()).isEqualTo(sizeBeforeDelete);
    }

    @Test
    void getByName() {
        Genre author = genreRepository.findByName(GENRE1.getName()).orElseThrow(() -> new NotFoundException("Genre not found"));
        assertThat(author.toString()).isEqualTo(GENRE1.toString());
    }

    @Test
    void getByWrongName() {
        assertThrows(NotFoundException.class, () -> genreRepository.findByName("Wrong name").orElseThrow(() -> new NotFoundException("Genre not found")));
    }


}
