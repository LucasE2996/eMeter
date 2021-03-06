package emonitor.app.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Embeddable
public class Report implements Serializable {

    @Getter private double dayAverage;
    @Getter private double weekAverage;
    @Getter private double monthAverage;
    @Lob
    @Getter private ArrayList<Watt> dayData;
    @Lob
    @Getter private ArrayList<Watt> weekData;
    @Lob
    @Getter private ArrayList<Watt> monthData;

    public Report(double dayAverage, double weekAverage, double monthAverage) {
        this.dayAverage = dayAverage;
        this.weekAverage = weekAverage;
        this.monthAverage = monthAverage;
        this.dayData = new ArrayList<>();
        this.weekData = new ArrayList<>();
        this.monthData = new ArrayList<>();
    }

    protected Report() {}

    public void addData(Watt data) {
        this.addDayData(data);
        this.addWeekData(data);
        this.addMonthData(data);
        this.calculateDayAverage();
        this.calculateWeekAverage();
        this.calculateMonthAverage();
    }

    private void calculateDayAverage() {
        this.dayAverage = this.sumTotal(this.dayData) / this.dayData.size();
    }

    private void calculateWeekAverage() {
        this.weekAverage = this.sumTotal(this.weekData) / this.weekData.size();
    }

    private void calculateMonthAverage() {
        this.monthAverage = this.sumTotal(this.monthData) / this.monthData.size();
    }

    private void addDayData(Watt value) {
        if (this.getDayData().size() > 0) {
            if (this.getDayData().get(0).day() != value.day() ) {
                this.getDayData().clear();
                this.addData(value);
            } else this.dayData.add(value);
        } else this.dayData.add(value);
    }

    private void addWeekData(Watt value) {
        if (this.getWeekData().size() > 0) {
            if ((this.weekData.get(0).day() - this.weekData.get(this.weekData.size() - 1).day()) < -5 ) {
                this.getWeekData().clear();
                this.addData(value);
            } else this.weekData.add(value);
        } else this.weekData.add(value);
    }

    private void addMonthData(Watt value) {
        if (this.getMonthData().size() > 0) {
            if (this.getMonthData().get(0).month() != value.month()) {
                this.monthData.clear();
                this.monthData.add(value);
            } else this.monthData.add(value);
        } else this.monthData.add(value);
    }

    private double sumTotal(ArrayList<Watt> value) {
        return value.stream()
                .mapToDouble(Watt::getWatts)
                .sum();
    }
}
