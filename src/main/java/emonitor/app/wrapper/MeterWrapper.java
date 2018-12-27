package emonitor.app.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import emonitor.app.domain.Monitor;
import emonitor.app.serialization.MeterSerializer;
import lombok.Getter;

@JsonSerialize(using = MeterSerializer.class)
public class MeterWrapper {

    @Getter private final Monitor monitor;

    public MeterWrapper(Monitor monitor) {
        this.monitor = monitor;
    }
}
