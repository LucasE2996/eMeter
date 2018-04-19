package emonitor.app.restapi;

import emonitor.app.services.RestError;
import emonitor.app.services.UserService;
import emonitor.app.wrapper.UserWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<?> getUser(
            @PathVariable int id,
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/user/")
                .path(String.valueOf(id))
                .build()
                .toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        try {
            UserWrapper wrapper = new UserWrapper(userService.getUser(id));
            return new ResponseEntity<>(wrapper, responseHeaders, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            RestError error = new RestError(4, "The user " + id + "could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/user/new", consumes = "application/json")
    public ResponseEntity<?> createUser(
            @RequestBody UserWrapper wrapper,
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/user/new")
                .build().toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        userService.save(wrapper.getUser());
        return new ResponseEntity<>(wrapper, responseHeaders, HttpStatus.CREATED);
    }
}
