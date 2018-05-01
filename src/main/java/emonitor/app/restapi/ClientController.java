package emonitor.app.restapi;

import emonitor.app.services.RestError;
import emonitor.app.services.ClientService;
import emonitor.app.wrapper.UserWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
public class ClientController {

    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    public ClientController(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/user/{id}/detail", produces = "application/json")
    public ResponseEntity<?> getUser(
            @PathVariable int id,
            Authentication auth,
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/user/")
                .path(String.valueOf(id))
                .path("detail").build().toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        try {
            final UserWrapper wrapper2 = (UserWrapper) auth.getPrincipal();
            final UserWrapper wrapper = new UserWrapper(clientService.getUser(id));
            if (wrapper.getUsername().equals(wrapper2.getUsername()))
                return new ResponseEntity<>(wrapper, responseHeaders, HttpStatus.OK);
            RestError error = new RestError(403, "You have no access to this page");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.FORBIDDEN);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            RestError error = new RestError(401, "The client with ID: '" + id + "' could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> createUser(
            @RequestBody UserWrapper wrapper,
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/register")
                .build().toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        wrapper.getClient().setPassword(passwordEncoder.encode(wrapper.getPassword()));
        clientService.save(wrapper.getClient());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
}
