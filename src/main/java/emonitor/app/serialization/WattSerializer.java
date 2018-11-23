package emonitor.app.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import emonitor.app.wrapper.WattWrapper;

import java.io.IOException;

public class WattSerializer extends JsonSerializer<WattWrapper> {
    @Override
    public void serialize(WattWrapper watt, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("watts", watt.getWatt().getWatts());
        jsonGenerator.writeNumberField("voltage", watt.getWatt().getVoltage());
        jsonGenerator.writeNumberField("current", watt.getWatt().getCurrent());
        jsonGenerator.writeNumberField("maxValue", watt.getWatt().getMaxValue());
        jsonGenerator.writeNumberField("minValue", watt.getWatt().getMinValue());
        jsonGenerator.writeStringField("date", watt.getWatt().getDate().toString());
        jsonGenerator.writeStringField("month", Integer.toString(watt.getWatt().month()));
        jsonGenerator.writeStringField("day", Integer.toString(watt.getWatt().day()));
        jsonGenerator.writeStringField("hour", Integer.toString(watt.getWatt().hour()));
        jsonGenerator.writeEndObject();
    }
}
