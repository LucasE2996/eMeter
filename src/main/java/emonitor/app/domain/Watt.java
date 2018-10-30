package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Embeddable
public class Watt {
    @Getter @Setter private double value;
    @Getter @Setter private double maxValue;
    @Getter @Setter private double minValue;
    @Getter @Setter private Date date;
    private Calendar calendar;

    public Watt(double power, Date date) {
        this.value = power;
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
        this.minValue = this.value < this.minValue ? this.value : this.minValue;
        this.maxValue = this.value > this.maxValue ? this.value : this.maxValue;
    }
}
