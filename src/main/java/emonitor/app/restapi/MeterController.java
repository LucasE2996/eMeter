package emonitor.app.restapi;

import emonitor.app.domain.Client;
import emonitor.app.domain.Meter;
import emonitor.app.services.ClientService;
import emonitor.app.services.MeterService;
import emonitor.app.services.RestError;
import emonitor.app.domain.ThingSpeakAdapter;
import emonitor.app.services.ThingSpeakService;
import emonitor.app.wrapper.MeterWrapper;
import emonitor.app.wrapper.UserWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class MeterController {

    private final MeterService service;
    private final ClientService clientService;
    private final ThingSpeakService tsService;

    public MeterController(
            MeterService service,
            ClientService clientService,
            ThingSpeakService thingSpeakService) {
        this.service = service;
        this.clientService = clientService;
        this.tsService = thingSpeakService;
    }

    @PostMapping(value = "/user/{clientId}/new-meter",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> createMeter(
            @PathVariable int clientId,
            Authentication auth,
            UriComponentsBuilder ucb
            ) {
        URI location = ucb
                .path("/user/")
                .path(String.valueOf(clientId))
                .path("new-meter").build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        try {
            final UserWrapper userWrapper1 = (UserWrapper) auth.getPrincipal();
            final UserWrapper userWrapper = new UserWrapper(clientService.getUser(clientId));
            if (userWrapper.getUsername().equals(userWrapper1.getUsername())) {
                final Client user = userWrapper.getClient();
                final Meter meter = tsService.getMeterConverted();
                meter.setClient(user);
                service.save(meter);
                clientService.save(user);
                return new ResponseEntity<>(new MeterWrapper(meter), responseHeaders, HttpStatus.CREATED);
            } else {
                RestError error = new RestError(403, "You have no access to this page");
                return new ResponseEntity<>(error, responseHeaders, HttpStatus.FORBIDDEN);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            RestError error = new RestError(404, "User id: " + clientId + " could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        } catch (RestClientException e) {
            e.printStackTrace();
            RestError error = new RestError(404, "Could not get any element in ThingSpeak API");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user/{clientId}/meters",
            produces = "application/json")
    public ResponseEntity<?> getAllMeters(
            Authentication auth,
            @PathVariable int clientId,
            UriComponentsBuilder ucb) {
        URI location = ucb
                .path("/user/")
                .path(String.valueOf(clientId))
                .path("/meters").build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        final UserWrapper userWrapper1 = (UserWrapper) auth.getPrincipal();
        final UserWrapper userWrapper = new UserWrapper(clientService.getUser(clientId));
        if (userWrapper.getUsername().equals(userWrapper1.getUsername())) {
            final List<Meter> meters = service.getAll(clientId);
            final List<MeterWrapper> wrappers = new ArrayList<>();
            meters.forEach(meter -> wrappers.add(new MeterWrapper(meter)));
            return new ResponseEntity<>(wrappers, responseHeaders, HttpStatus.OK);
        }
        RestError error = new RestError(403, "You have no access to this page");
        return new ResponseEntity<>(error, responseHeaders, HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "user/{userId}/meter/{id}/updatePower",
            produces = "application/json")
    public ResponseEntity<?> getMeter(
            Authentication auth,
            @PathVariable int id,
            @PathVariable int userId,
            UriComponentsBuilder ucb) {
        URI location = ucb
                .path("/meter/")
                .path(String.valueOf(id))
                .path("/detail")
                .build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        try {
            final UserWrapper userWrapper1 = (UserWrapper) auth.getPrincipal();
                final UserWrapper userWrapper = new UserWrapper(clientService.getUser(userId));
                if (userWrapper.getUsername().equals(userWrapper1.getUsername())) {
                    Meter meter = service.get(id);
                    // Restriction: the tsService is static, to add new meter you have to modify this Class
                    // and add a new url
                    meter.updatePower(tsService.getMeterPower());
                    service.save(meter);
                    return new ResponseEntity<>(new MeterWrapper(meter), responseHeaders, HttpStatus.OK);
                } else {
                    RestError error = new RestError(403, "You have no access to this page");
                    return new ResponseEntity<>(error, responseHeaders, HttpStatus.FORBIDDEN);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            RestError error = new RestError(4, "Meter id: " + id + " could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
}
