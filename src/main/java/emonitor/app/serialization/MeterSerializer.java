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
        jsonGenerator.writeNumberField("id", meterWrapper.getMonitor().getId());
        jsonGenerator.writeNumberField("client_id", meterWrapper.getMonitor().getClient().getId());
        jsonGenerator.writeNumberField("channel_id", meterWrapper.getMonitor().getChannel());
        jsonGenerator.writeStringField("name", meterWrapper.getMonitor().getName());
        jsonGenerator.writeNumberField("nominalPower", meterWrapper.getMonitor().getNominalPower());
        jsonGenerator.writeNumberField("diversion", meterWrapper.getMonitor().getDiversion());
        jsonGenerator.writeObjectField("watt", new WattWrapper(meterWrapper.getMonitor().getWatt()));
        jsonGenerator.writeObjectField("report", new ReportWrapper(meterWrapper.getMonitor().getReport()));
        jsonGenerator.writeEndObject();
    }
}
