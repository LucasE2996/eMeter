package emonitor.app.services;

import emonitor.app.domain.Meter;
import emonitor.app.domain.ThingSpeakAdapter;
import emonitor.app.domain.Watt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Optional;

@Service
public class ThingSpeakService {

    private final RestTemplate restTemplate;
    private final String apiUrl = "https://api.thingspeak.com/channels/456741/feeds.json?api_key=K7Q13A7CLNUZTMPX&results=1";

    public ThingSpeakService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * If the return value is 0 is probably because one the values returned null from ThingSpeak API.
     *
     * @return The current power value from TS API
     */
    public double getMeterPower() {
        final double current = this.getMeterCurrent().isPresent() ? this.getMeterCurrent().get() : 0;
        final double voltage = this.getMeterVoltage().isPresent() ? this.getMeterVoltage().get() : 0;
        return current * voltage;
    }

    /**
     * Converts the current meter from ThingSpeak API to Meter model.
     *
     * @return {@link Meter} The meter converted.
     */
    public Meter getMeterConverted() {
        final ThingSpeakAdapter ts = this.restTemplate.getForObject(this.apiUrl, ThingSpeakAdapter.class);
        return new Meter(
                ts.getName(),
                ts.getId(),
                new Watt(this.getMeterPower(), Date.valueOf(ts.getCreatedDate()))
        );
    }

    private Optional<Double> getMeterCurrent() {
        final ThingSpeakAdapter currentPayload = this.restTemplate.getForObject(this.apiUrl, ThingSpeakAdapter.class);
        return Optional.of(Double.parseDouble(currentPayload.getField1()));
    }

    private Optional<Double> getMeterVoltage() {
        final ThingSpeakAdapter voltagePayload = this.restTemplate.getForObject(this.apiUrl, ThingSpeakAdapter.class);
        return Optional.of(Double.parseDouble(voltagePayload.getField2()));
    }
}
