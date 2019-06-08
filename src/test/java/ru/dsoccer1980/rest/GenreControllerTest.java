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
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.repository.UserRepository;
import ru.dsoccer1980.security.JWTWebSecurityConfig;
import ru.dsoccer1980.security.JwtTokenUtil;
import ru.dsoccer1980.security.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import ru.dsoccer1980.service.GenreService;
import ru.dsoccer1980.service.UserDetailsServiceImpl;
import ru.dsoccer1980.web.rest.GenreController;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dsoccer1980.TestData.GENRE1;


@ExtendWith(SpringExtension.class)
@WebMvcTest({GenreController.class})
@Import({JWTWebSecurityConfig.class, JwtUnAuthorizedResponseAuthenticationEntryPoint.class,
        UserDetailsServiceImpl.class, JwtTokenUtil.class})
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

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
    @DisplayName("Пользователь может смотреть жанры")
    void testGetAllUser() throws Exception {
        given(genreService.getAll())
                .willReturn(List.of(new Genre("Фантастика")));

        mvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Фантастика")));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может получить жанр по ид")
    void testGetByIdUser() throws Exception {
        given(genreService.get("1"))
                .willReturn(new Genre("Фантастика"));

        mvc.perform(get("/genre/1"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может получить жанр по ид")
    void testGetByIdAdmin() throws Exception {
        given(genreService.get("1"))
                .willReturn(new Genre("Фантастика"));

        mvc.perform(get("/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Фантастика")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может удалить жанр")
    void testDeleteAdmin() throws Exception {
        doNothing().when(genreService).delete("100000000000000000000001");
        mvc.perform(delete("/genre").param("id", "100000000000000000000001"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может удалить жанр")
    void testDeleteUser() throws Exception {
        doNothing().when(genreService).delete("100000000000000000000001");
        mvc.perform(delete("/genre").param("id", "100000000000000000000001"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может создавать жанр")
    void testPostAdmin() throws Exception {
        mvc.perform(post("/genre")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(GENRE1)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может создавать жанр")
    void testPostUser() throws Exception {
        mvc.perform(post("/genre")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(GENRE1)))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Администратор может обновлять жанр")
    void testPutAdmin() throws Exception {
        mvc.perform(put("/genre")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(GENRE1)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Пользователь не может обновлять жанр")
    void testPutUser() throws Exception {
        mvc.perform(put("/genre")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(GENRE1)))
                .andExpect(status().is(403));
    }

}
