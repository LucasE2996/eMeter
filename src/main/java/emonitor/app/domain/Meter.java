package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Integer id;
    @Getter private String channel_id;
    @Getter @Setter private String name;
    @Getter private Watt watt;
    @Getter private Report report;
    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter private Client client;

    public Meter(String defaultName, String channel_id, Watt watt) {
        this.name = defaultName;
        this.channel_id = channel_id;
        this.watt = watt;
        this.report = new Report(0,0,0);
    }

    protected Meter() {}

}
