package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
public class Watt {
    @Getter @Setter private double current;
    @Getter @Setter private double voltage;
    @Getter private double value;
    @Getter @Setter private double maxValue;
    @Getter @Setter private double minValue;

    public Watt(double current, double voltage) {
        this.current = current;
        this.voltage = voltage;
    }

    protected Watt() {}

    private void calcValue() {
        this.value = this.current * this.voltage;
        this.minValue = this.value < this.minValue ? this.value : this.minValue;
        this.maxValue = this.value > this.maxValue ? this.value : this.maxValue;
    }
}
