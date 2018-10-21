package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
public class Watt {
    @Getter @Setter private double value;
    @Getter @Setter private double maxValue;
    @Getter @Setter private double minValue;

    public Watt(double power) {
        this.value = power;
    }

    protected Watt() {}

    private void calcMinAndMaxValue() {
        this.minValue = this.value < this.minValue ? this.value : this.minValue;
        this.maxValue = this.value > this.maxValue ? this.value : this.maxValue;
    }
}
