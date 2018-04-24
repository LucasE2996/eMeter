package emonitor.app.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import emonitor.app.domain.Meter;
import emonitor.app.wrapper.UserWrapper;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<UserWrapper> {
    @Override
    public void serialize(UserWrapper wrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", wrapper.getClient().getName());
        jsonGenerator.writeStringField("lastName", wrapper.getClient().getLastName());
        jsonGenerator.writeStringField("email", wrapper.getClient().getEmail());
        jsonGenerator.writeEndObject();
    }
}
