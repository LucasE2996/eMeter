package emonitor.app.restapi;

import emonitor.app.domain.Client;
import emonitor.app.domain.Meter;
import emonitor.app.domain.Report;
import emonitor.app.domain.Watt;
import emonitor.app.services.ClientService;
import emonitor.app.services.MeterService;
import emonitor.app.services.RestError;
import emonitor.app.wrapper.MeterWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class MeterController {

    private final MeterService service;
    private final ClientService clientService;

    public MeterController(MeterService service, ClientService clientService) {
        this.service = service;
        this.clientService = clientService;
    }

    @PostMapping(value = "/user/{clientId}/new-meter",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> createMeter(
            @PathVariable int clientId,
            UriComponentsBuilder ucb) {
        URI location = ucb
                .path("/user/")
                .path(String.valueOf(clientId))
                .path("new-meter").build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        final Meter meter = new Meter("Meter", new Watt(0), new Report(0,0,0));
        final MeterWrapper wrapper = new MeterWrapper(meter);
        try {
            final Client client = clientService.getUser(clientId);
            client.addMeter(wrapper.getMeter());
            wrapper.getMeter().setClient(client);
            service.save(wrapper.getMeter());
            clientService.save(client);
            return new ResponseEntity<>(wrapper, responseHeaders, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            RestError error = new RestError(4, "Meter id: " + clientId + "could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/user/{clientId}/meters",
            produces = "application/json")
    public ResponseEntity<?> getAllMeters(
            @PathVariable int clientId,
            UriComponentsBuilder ucb) {
        URI location = ucb
                .path("/user/")
                .path(String.valueOf(clientId))
                .path("/meters").build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        final List<Meter> meters = service.getAll(clientId);
        final List<MeterWrapper> wrappers = new ArrayList<>();
        meters.forEach(meter -> wrappers.add(new MeterWrapper(meter)));
        return new ResponseEntity<>(wrappers, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/meter/{id}/detail",
            produces = "application/json")
    public ResponseEntity<?> getMeter(
            @PathVariable int id,
            UriComponentsBuilder ucb) {
        URI location = ucb
                .path("/meter/")
                .path(String.valueOf(id))
                .path("/detail")
                .build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        try {
            final MeterWrapper wrapper = new MeterWrapper(service.get(id));
            return new ResponseEntity<>(wrapper, responseHeaders, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            RestError error = new RestError(4, "Meter id: " + id + "could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
}
