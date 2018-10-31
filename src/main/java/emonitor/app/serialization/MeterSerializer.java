package emonitor.app.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import emonitor.app.wrapper.MeterWrapper;
import emonitor.app.wrapper.ReportWrapper;
import emonitor.app.wrapper.WattWrapper;

import java.io.IOException;

public class MeterSerializer extends JsonSerializer<MeterWrapper> {
    @Override
    public void serialize(MeterWrapper meterWrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", meterWrapper.getMeter().getId());
        jsonGenerator.writeNumberField("client_id", meterWrapper.getMeter().getClient().getId());
        jsonGenerator.writeNumberField("channel_id", meterWrapper.getMeter().getChannel());
        jsonGenerator.writeStringField("name", meterWrapper.getMeter().getName());
        jsonGenerator.writeObjectField("watt", new WattWrapper(meterWrapper.getMeter().getWatt()));
        jsonGenerator.writeObjectField("report", new ReportWrapper(meterWrapper.getMeter().getReport()));
        jsonGenerator.writeEndObject();
    }
}
