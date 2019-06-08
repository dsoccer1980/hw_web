package ru.dsoccer1980.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.dsoccer1980.domain.Role;
import ru.dsoccer1980.domain.User;
import ru.dsoccer1980.repository.UserRepository;
import ru.dsoccer1980.security.JWTWebSecurityConfig;
import ru.dsoccer1980.security.JwtTokenRequest;
import ru.dsoccer1980.security.JwtTokenUtil;
import ru.dsoccer1980.security.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import ru.dsoccer1980.service.UserDetailsServiceImpl;
import ru.dsoccer1980.web.rest.AuthenticationController;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({AuthenticationController.class})
@Import({JWTWebSecurityConfig.class, JwtUnAuthorizedResponseAuthenticationEntryPoint.class,
        UserDetailsServiceImpl.class, JwtTokenUtil.class})
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

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
    void testAuthenticateUser() throws Exception {
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(new User(1L, "user", new BCryptPasswordEncoder().encode("password"), Set.of(Role.USER))));
        mvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(new JwtTokenRequest("user", "password"))))
                .andExpect(status().isOk());
    }

    @Test
    void testAuthenticateWithWrongPassword() throws Exception {
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(new User(1L, "user", new BCryptPasswordEncoder().encode("password"), Set.of(Role.USER))));
        mvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(new JwtTokenRequest("user", "wrong password"))))
                .andExpect(status().is(401));
    }

}
