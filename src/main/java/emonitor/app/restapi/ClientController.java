package emonitor.app.restapi;

import emonitor.app.domain.Client;
import emonitor.app.services.RestError;
import emonitor.app.services.ClientService;
import emonitor.app.wrapper.UserWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class ClientController {

    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    public ClientController(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllUsers(
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/user")
                .path("/all").build().toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        final List<Client> users = clientService.getAll();
        return new ResponseEntity<>(users, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/detail", produces = "application/json")
    public ResponseEntity<?> getUser(
            Authentication auth,
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/user")
                .path("/detail").build().toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        final UserWrapper userWrapper = (UserWrapper) auth.getPrincipal();
        try {
            final UserDetails userDetails = clientService.loadUserByUsername(userWrapper.getUsername());
            if (userDetails.getUsername().equals(userWrapper.getUsername()) &&
                userDetails.getPassword().equals(userWrapper.getPassword()))
                return new ResponseEntity<>(userDetails, responseHeaders, HttpStatus.OK);
            RestError error = new RestError(403, "You have no access to this page");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.FORBIDDEN);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            RestError error = new RestError(401, "The client with name '" + userWrapper.getUsername() + "' could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> createUser(
            @RequestBody UserWrapper wrapper,
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/user")
                .path("/register")
                .build().toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        wrapper.getClient().setPassword(passwordEncoder.encode(wrapper.getPassword()));
        clientService.save(wrapper.getClient());
        return new ResponseEntity<>(wrapper, responseHeaders, HttpStatus.CREATED);
    }
}
