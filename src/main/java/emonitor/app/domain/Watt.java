package emonitor.app.domain;

import lombok.Getter;

public class Watt {

    @Getter private double value;

    public Watt(double value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
