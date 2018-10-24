package emonitor.app.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;

@Embeddable
public class Report {

    @Getter private double dayAvarage;
    @Getter private double weekAverage;
    @Getter private double monthAverage;
    @Getter private ArrayList<Watt> dayData;
    @Getter private ArrayList<Watt> weekData;
    @Getter private ArrayList<Watt> monthData;

    public Report(double dayAvarage, double weekAverage, double monthAverage) {
        this.dayAvarage = dayAvarage;
        this.weekAverage = weekAverage;
        this.monthAverage = monthAverage;
    }

    protected Report() {}

    public double calculateDayAvarage() {
        return this.sumTotal(this.dayData) / this.dayData.size();
    }

    public double calculateWeelAverage() {
        return this.sumTotal(this.weekData) / this.weekData.size();
    }

    public double calculateMonthAvarage() {
        return this.sumTotal(this.monthData) / this.monthData.size();
    }

    private double sumTotal(ArrayList<Watt> value) {
        return value.stream()
                .mapToDouble(data -> data.getValue())
                .sum();
    }
}
