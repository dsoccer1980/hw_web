package ru.dsoccer1980.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.dsoccer1980.domain.Role;
import ru.dsoccer1980.domain.User;
import ru.dsoccer1980.security.AuthenticationException;
import ru.dsoccer1980.security.JwtTokenRequest;
import ru.dsoccer1980.security.JwtTokenResponse;
import ru.dsoccer1980.security.JwtTokenUtil;
import ru.dsoccer1980.service.UserDetailsServiceImpl;

import java.security.Principal;
import java.util.Objects;
import java.util.Set;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.99.100:3000"})
public class AuthenticationController {

    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/isadmin")
    public boolean isAdmin(Principal currentUser) {
        return ((User) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal()).getRoles().contains(Role.ADMIN);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
            throws AuthenticationException {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if (!((User) userDetails).getRoles().equals(Set.of(Role.ANONYM))) {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        }

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }
}

