package emonitor.app.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Integer id;
    @Getter private String name;
    @Getter private String lastName;
    @Getter private String email;
    @Getter private String password;
    @OneToMany(mappedBy = "user")
    private List<Meter> meters;

    public User(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password  = password;
    }

    protected User() {}

    public List<Meter> getMeters() {
        return Collections.unmodifiableList(meters);
    }

    public void addMeter(Meter meter) {
        meters.add(meter);
    }
}
