package emonitor.app.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import emonitor.app.domain.Meter;
import emonitor.app.serialization.MeterSerializer;
import lombok.Getter;

@JsonSerialize(using = MeterSerializer.class)
public class MeterWrapper {

    @Getter private final Meter meter;

    public MeterWrapper(Meter meter) {
        this.meter = meter;
    }
}
