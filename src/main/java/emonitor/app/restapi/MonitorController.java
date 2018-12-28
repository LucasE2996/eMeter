package emonitor.app.restapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import emonitor.app.domain.Client;
import emonitor.app.domain.Monitor;
import emonitor.app.services.ClientService;
import emonitor.app.services.MeterService;
import emonitor.app.services.RestError;
import emonitor.app.services.ThingSpeakService;
import emonitor.app.wrapper.MeterWrapper;
import emonitor.app.wrapper.UserWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private final MeterService service;
    private final ClientService clientService;
    private final ThingSpeakService tsService;

    public MonitorController(
            MeterService service,
            ClientService clientService,
            ThingSpeakService thingSpeakService) {
        this.service = service;
        this.clientService = clientService;
        this.tsService = thingSpeakService;
    }

    @PostMapping(value = "/{clientId}/new-monitor",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> createMonitor(
            @PathVariable int clientId,
            @RequestBody String nominalValue,
            UriComponentsBuilder ucb
            ) {
        URI location = ucb
                .path("/monitor/")
                .path(String.valueOf(clientId))
                .path("new-monitor").build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        try {
            final UserWrapper userWrapper = new UserWrapper(clientService.getUser(clientId));
            final Client user = userWrapper.getClient();
            final Monitor monitor = tsService.getMeterConverted();
            JsonObject jobj = new Gson().fromJson(nominalValue, JsonObject.class);
            monitor.setNominalPower(jobj.get("nominalValue").getAsDouble());
            monitor.setClient(user);
            service.save(monitor);
            clientService.save(user);
            return new ResponseEntity<>(new MeterWrapper(monitor), responseHeaders, HttpStatus.CREATED);
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

    @GetMapping(value = "/{clientId}/monitors",
            produces = "application/json")
    public ResponseEntity<?> getAllMonitors(
            @PathVariable int clientId,
            UriComponentsBuilder ucb) {
        URI location = ucb
                .path("/monitor/")
                .path(String.valueOf(clientId))
                .path("/monitors").build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        final List<Monitor> monitors = service.getAll(clientId);
        final List<MeterWrapper> wrappers = new ArrayList<>();
        monitors.forEach(meter -> wrappers.add(new MeterWrapper(meter)));
        return new ResponseEntity<>(wrappers, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/monitors/{id}/detail",
            produces = "application/json")
    public ResponseEntity<?> getMeter(
            @PathVariable int id,
            @PathVariable int userId,
            UriComponentsBuilder ucb) {
        URI location = ucb
                .path("/monitor/")
                .path(String.valueOf(userId))
                .path("/detail/")
                .path(String.valueOf(id))
                .build().toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        try {
            Monitor monitor = service.get(id);
            // Restriction: the tsService is static, to add new monitor you have to modify this Class
            // and add a new url
            monitor.updateValues(
                    tsService.getMeterVoltage().isPresent() ? tsService.getMeterVoltage().get() : 0,
                    tsService.getMeterCurrent().isPresent() ? tsService.getMeterCurrent().get() : 0,
                    tsService.getMeterPower()
            );
            service.save(monitor);
            return new ResponseEntity<>(new MeterWrapper(monitor), responseHeaders, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            RestError error = new RestError(4, "Monitor id: " + id + " could not be found");
            return new ResponseEntity<>(error, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
}
