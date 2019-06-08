package ru.dsoccer1980.rest;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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


    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    void test() throws Exception {
        given(authorService.getAll())
                .willReturn(List.of(new Author("Стругацкий")));

        mvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Стругацкий")));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    void test2() throws Exception {
        doNothing().when(authorService).delete("100000000000000000000001");
//        given(authorService.delete("100000000000000000000001")
//                .getMock();

        mvc.perform(delete("/author").param("id", "100000000000000000000001"))
                .andExpect(status().isOk());
        // .andExpect(content().string(containsString("Стругацкий")));
    }

}
