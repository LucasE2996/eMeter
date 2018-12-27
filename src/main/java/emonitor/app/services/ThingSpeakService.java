package emonitor.app.services;

import emonitor.app.domain.Monitor;
import emonitor.app.domain.ThingSpeakAdapter;
import emonitor.app.domain.Watt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class ThingSpeakService {

    private final RestTemplate restTemplate;
    private final SimpleDateFormat dateFormat;
    private final String API_URL = "https://api.thingspeak.com/channels/456741/feeds.json?api_key=K7Q13A7CLNUZTMPX&results=1";

    public ThingSpeakService(RestTemplate restTemplate, SimpleDateFormat dateFormat) {
        this.restTemplate = restTemplate;
        this.dateFormat = dateFormat;
    }

    /**
     * If the return watts is 0 is probably because one the values returned null from ThingSpeak API.
     *
     * @return The current power watts from TS API
     */
    public double getMeterPower() {
        final double current = this.getMeterCurrent().isPresent() ? this.getMeterCurrent().get() : 0;
        final double voltage = this.getMeterVoltage().isPresent() ? this.getMeterVoltage().get() : 0;
        return current * voltage;
    }

    /**
     * Converts the current meter from ThingSpeak API to Monitor model.
     *
     * @return {@link Monitor} The meter converted.
     */
    public Monitor getMeterConverted() {
        final ThingSpeakAdapter ts = this.restTemplate.getForObject(this.API_URL, ThingSpeakAdapter.class);
        return new Monitor(
                ts.getName(),
                ts.getId(),
                new Watt(
                        this.getMeterVoltage().isPresent() ? this.getMeterVoltage().get() : 0,
                        this.getMeterCurrent().isPresent() ? this.getMeterCurrent().get() : 0,
                        this.getMeterPower(),
                        this.convertDate(ts.getCreatedDate())
                )
        );
    }

    public Optional<Double> getMeterCurrent() {
        final ThingSpeakAdapter currentPayload = this.restTemplate.getForObject(this.API_URL, ThingSpeakAdapter.class);
        return Optional.of(Double.parseDouble(currentPayload.getField1()));
    }

    public Optional<Double> getMeterVoltage() {
        final ThingSpeakAdapter voltagePayload = this.restTemplate.getForObject(this.API_URL, ThingSpeakAdapter.class);
        return Optional.of(Double.parseDouble(voltagePayload.getField2()));
    }

    private Date convertDate(String date) {
        try {
            String str = date.replace("T", "");
            date = str.replace("Z", "");
            return this.dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
