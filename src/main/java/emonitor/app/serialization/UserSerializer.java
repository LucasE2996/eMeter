package emonitor.app.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import emonitor.app.wrapper.UserWrapper;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<UserWrapper> {
    @Override
    public void serialize(UserWrapper wrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", wrapper.getId().toString());
        jsonGenerator.writeStringField("username", wrapper.getUsername());
        jsonGenerator.writeStringField("email", wrapper.getClient().getEmail());
        jsonGenerator.writeEndObject();
    }
}
