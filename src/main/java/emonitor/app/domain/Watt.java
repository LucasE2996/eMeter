package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
public class Watt {
    @Getter @Setter private double value;

    public Watt(double value) {
        this.value = value;
    }

    protected Watt() {}
}
