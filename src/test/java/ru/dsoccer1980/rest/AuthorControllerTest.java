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
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.repository.UserRepository;
import ru.dsoccer1980.security.JWTWebSecurityConfig;
import ru.dsoccer1980.security.JwtTokenUtil;
import ru.dsoccer1980.security.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import ru.dsoccer1980.service.AuthorService;
import ru.dsoccer1980.service.UserDetailsServiceImpl;
import ru.dsoccer1980.web.rest.AuthorController;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dsoccer1980.TestData.AUTHOR1;


@ExtendWith(SpringExtension.class)
@WebMvcTest({AuthorController.class})
@Import({JWTWebSecurityConfig.class, JwtUnAuthorizedResponseAuthenticationEntryPoint.class,
        UserDetailsServiceImpl.class, JwtTokenUtil.class})
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserRepository userRepository;

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
    @DisplayName("Пользователь может смотреть авторов")
    void testGetAllUser() throws Exception {
        given(authorService.getAll())
                .willReturn(List.of(new Author("Стругацкий")));

        mvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Стругацкий")));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может получить автора по ид")
    void testGetByIdUser() throws Exception {
        given(authorService.get("1"))
                .willReturn(new Author("Стругацкий"));

        mvc.perform(get("/author/1"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может получить автора по ид")
    void testGetByIdAdmin() throws Exception {
        given(authorService.get("1"))
                .willReturn(new Author("Стругацкий"));

        mvc.perform(get("/author/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Стругацкий")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может удалить автора")
    void testDeleteAdmin() throws Exception {
        doNothing().when(authorService).delete("100000000000000000000001");
        mvc.perform(delete("/author").param("id", "100000000000000000000001"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может удалить автора")
    void testDeleteUser() throws Exception {
        doNothing().when(authorService).delete("100000000000000000000001");
        mvc.perform(delete("/author").param("id", "100000000000000000000001"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может создавать автора")
    void testPostAdmin() throws Exception {
        mvc.perform(post("/author")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(AUTHOR1)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может создавать автора")
    void testPostUser() throws Exception {
        mvc.perform(post("/author")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(AUTHOR1)))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может обновлять автора")
    void testPutAdmin() throws Exception {
        mvc.perform(put("/author")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(AUTHOR1)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может обновлять автора")
    void testPutUser() throws Exception {
        mvc.perform(put("/author")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(AUTHOR1)))
                .andExpect(status().is(403));
    }

}
