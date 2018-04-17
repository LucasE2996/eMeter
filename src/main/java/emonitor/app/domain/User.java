package emonitor.app.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class User {

    @Getter private String name;
    @Getter private String lastname;
    @Getter private String email;
    @Getter private String password;
    private List<Meter> meters;

    public User(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password  = password;
    }

    public List<Meter> getMeters() {
        return Collections.unmodifiableList(meters);
    }

    public void addMeter(Meter meter) {
        meters.add(meter);
    }
}
