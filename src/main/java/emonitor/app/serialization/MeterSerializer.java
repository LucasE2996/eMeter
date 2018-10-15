package emonitor.app.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import emonitor.app.wrapper.MeterWrapper;

import java.io.IOException;

public class MeterSerializer extends JsonSerializer<MeterWrapper> {
    @Override
    public void serialize(MeterWrapper meterWrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", meterWrapper.getMeter().getId());
        jsonGenerator.writeNumberField("client_id", meterWrapper.getMeter().getClient().getId());
        jsonGenerator.writeStringField("channel_id", meterWrapper.getMeter().getChannel_id());
        jsonGenerator.writeStringField("name", meterWrapper.getMeter().getName());
        jsonGenerator.writeObjectField("watt", meterWrapper.getMeter().getWatt());
        jsonGenerator.writeObjectField("report", meterWrapper.getMeter().getReport());
        jsonGenerator.writeEndObject();
    }
}
