package ru.dsoccer1980.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.dto.BookDto;
import ru.dsoccer1980.integration.BookGateway;
import ru.dsoccer1980.repository.UserRepository;
import ru.dsoccer1980.security.JWTWebSecurityConfig;
import ru.dsoccer1980.security.JwtTokenUtil;
import ru.dsoccer1980.security.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import ru.dsoccer1980.service.BookService;
import ru.dsoccer1980.service.UserDetailsServiceImpl;
import ru.dsoccer1980.web.rest.BookController;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dsoccer1980.TestData.BOOK1;


@ExtendWith(SpringExtension.class)
@WebMvcTest({BookController.class})
@Import({JWTWebSecurityConfig.class, JwtUnAuthorizedResponseAuthenticationEntryPoint.class,
        UserDetailsServiceImpl.class, JwtTokenUtil.class})
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookGateway bookGateway;

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь может смотреть книги")
    void testGetAllUser() throws Exception {
        given(bookService.getAll())
                .willReturn(List.of(new Book("Имя книги", null, null)));

        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Имя книги")));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может получить книгу по ид")
    void testGetByIdUser() throws Exception {
        given(bookService.get("1"))
                .willReturn(new Book("Имя книги", null, null));

        mvc.perform(get("/book/1"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может получить книгу по ид")
    void testGetByIdAdmin() throws Exception {
        given(bookService.get("1"))
                .willReturn(new Book("Имя книги", null, null));

        mvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Имя книги")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может удалить книгу")
    void testDeleteAdmin() throws Exception {
        doNothing().when(bookService).delete("100000000000000000000001");
        mvc.perform(delete("/book").param("id", "100000000000000000000001"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может удалить книгу")
    void testDeleteUser() throws Exception {
        doNothing().when(bookService).delete("100000000000000000000001");
        mvc.perform(delete("/book").param("id", "100000000000000000000001"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может создавать книгу")
    void testPostAdmin() throws Exception {
        BookDto bookDto = new BookDto(String.valueOf(111111111110L), "Book name", null, null, null);
        given(bookGateway.process(bookDto)).willReturn(bookDto);
        Book book = BookDto.getBook(bookDto);
        mvc.perform(post("/book")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(book)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может создавать книгу")
    void testPostUser() throws Exception {
        mvc.perform(post("/book")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(BOOK1)))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может обновлять книгу")
    void testPutAdmin() throws Exception {
        BookDto bookDto = new BookDto(String.valueOf(111111111110L), "Book name", null, null, null);
        given(bookGateway.process(bookDto)).willReturn(bookDto);
        Book book = BookDto.getBook(bookDto);
        mvc.perform(put("/book")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(book)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может обновлять книгу")
    void testPutUser() throws Exception {
        mvc.perform(put("/book")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(BOOK1)))
                .andExpect(status().is(403));
    }

}
