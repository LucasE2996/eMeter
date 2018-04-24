package emonitor.app.restapi;

import emonitor.app.services.RestError;
import emonitor.app.services.ClientService;
import emonitor.app.wrapper.UserWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/user/{id}/detail", produces = "application/json")
    public ResponseEntity<?> getUser(
            @PathVariable int id,
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/client/")
                .path(String.valueOf(id))
                .path("detail").build().toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        try {
            UserWrapper wrapper = new UserWrapper(clientService.getUser(id));
            return new ResponseEntity<>(wrapper, responseHeaders, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            RestError error = new RestError(4, "The client with ID: '" + id + "' could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/user/new", consumes = "application/json")
    public ResponseEntity<?> createUser(
            @RequestBody UserWrapper wrapper,
            UriComponentsBuilder ucb) {
        final URI location = ucb
                .path("/client/new")
                .build().toUri();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        clientService.save(wrapper.getClient());
        return new ResponseEntity<>(wrapper, responseHeaders, HttpStatus.CREATED);
    }
}
