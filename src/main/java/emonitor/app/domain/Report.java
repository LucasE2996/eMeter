package emonitor.app.domain;

import lombok.Getter;

import javax.persistence.*;

@Embeddable
public class Report {

    @Getter private double dayAvarage;
    @Getter private double weekAverage;
    @Getter private double monthAverage;

    public Report(double dayAvarage, double weekAverage, double monthAverage) {
        this.dayAvarage = dayAvarage;
        this.weekAverage = weekAverage;
        this.monthAverage = monthAverage;
    }

    protected Report() {}

//    public double calculateDayAvarage() {
//    }
}
