package emonitor.app.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import emonitor.app.wrapper.MeterWrapper;
import emonitor.app.wrapper.ReportWrapper;

import java.io.IOException;

public class ReportSerializer extends JsonSerializer<ReportWrapper> {
    @Override
    public void serialize(ReportWrapper report, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("dayAverage", report.getReport().getDayAverage());
        jsonGenerator.writeNumberField("weekAverage", report.getReport().getWeekAverage());
        jsonGenerator.writeNumberField("monthAverage", report.getReport().getMonthAverage());
        jsonGenerator.writeEndObject();
    }
}
