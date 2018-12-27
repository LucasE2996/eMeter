package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Integer id;
    @Getter private Integer channel;
    @Getter @Setter private String name;
    @Getter private Watt watt;
    @Getter private Report report;
    @Getter @Setter private double nominalPower;
    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter private Client client;

    public Monitor(String defaultName, Integer channel, Watt watt) {
        this.name = defaultName;
        this.channel = channel;
        this.watt = watt;
        this.report = new Report(0,0,0);
    }

    protected Monitor() {}

    public void updateValues(double voltage, double current, double watts) {
        this.getWatt().setWatts(watts);
        this.getWatt().setVoltage(voltage);
        this.getWatt().setCurrent(current);
        this.getWatt().calcMinAndMaxValue();
        this.getReport().addData(this.getWatt());
    }

    public int getDiversion() {
        final Double diversion =  -100 + ((this.getWatt().getWatts() * 100) / this.getNominalPower());
        return diversion.intValue();
    }

}
