package ru.dsoccer1980.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.service.AuthorService;
import ru.dsoccer1980.service.BookService;
import ru.dsoccer1980.service.GenreService;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @Test
    void test() throws Exception {
        given(bookService.getAll())
                .willReturn(List.of(new Book("Книга", null, null)));

        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Книга")));
    }
}
