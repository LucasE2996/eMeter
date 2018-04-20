package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter private User user;

}
