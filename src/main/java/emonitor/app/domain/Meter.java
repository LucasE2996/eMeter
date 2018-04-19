package emonitor.app.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Integer id;
    @OneToOne
    @Getter private Watt watt;
    @OneToOne
    @Getter private Report report;
    @ManyToOne
    @Getter private User user;

}
