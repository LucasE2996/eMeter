package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Embeddable
public class Watt  implements Serializable {
    @Getter @Setter private double watts;
    @Getter @Setter private double current;
    @Getter @Setter private double voltage;
    @Getter @Setter private double maxValue;
    @Getter @Setter private double minValue;
    @Getter @Setter private Date date;
    private Calendar calendar;

    public Watt(double voltage, double current, double watts, Date date) {
        this.voltage = voltage;
        this.current = current;
        this.watts = watts;
        this.date = date;
        this.calendar = Calendar.getInstance();
    }

    protected Watt() {}

    public int day() {
        this.calendar.setTime(this.date);
        return this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int month() {
        this.calendar.setTime(this.date);
        return this.calendar.get(Calendar.MONTH);
    }

    public void calcMinAndMaxValue() {
        this.minValue = this.watts < this.minValue ? this.watts : this.minValue;
        this.maxValue = this.watts > this.maxValue ? this.watts : this.maxValue;
    }
}
