package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Integer id;
    @Getter private Integer channel;
    @Getter @Setter private String name;
    @Getter private Watt watt;
    @Getter @Setter private Report report;
    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter private Client client;

    public Meter(String defaultName, Integer channel, Watt watt) {
        this.name = defaultName;
        this.channel = channel;
        this.watt = watt;
        this.report = new Report(0,0,0);
    }

    protected Meter() {}

    public void updatePower(double power) {
        this.getWatt().setValue(power);
        // TODO - update report also
    }

    public double getPower() {
        return getWatt().getValue();
    }

}
