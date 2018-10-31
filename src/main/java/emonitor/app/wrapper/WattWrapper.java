package emonitor.app.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import emonitor.app.domain.Watt;
import emonitor.app.serialization.WattSerializer;
import lombok.Getter;

@JsonSerialize(using = WattSerializer.class)
public class WattWrapper {

    @Getter
    private final Watt watt;

    public WattWrapper(Watt watt) {
        this.watt = watt;
    }
}
