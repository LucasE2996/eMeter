package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Integer id;
    @Getter @Setter private String name;
    @Getter private Watt watt;
    @Getter private Report report;
    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter private Client client;

    public Meter(String defaultName, Watt watt, Report report) {
        this.name = defaultName;
        this.watt = watt;
        this.report = report;
    }

    protected Meter() {}

}
