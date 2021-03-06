package emonitor.app.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import emonitor.app.domain.Client;
import emonitor.app.wrapper.UserWrapper;

import java.io.IOException;

public class UserDeserializer extends JsonDeserializer<UserWrapper> {
    @Override
    public UserWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);

        final String name = jsonNode.get("username").asText();
        final String email = jsonNode.get("email").asText();
        final String password = jsonNode.get("password").asText();

        return new UserWrapper(new Client(name, email, password));
    }
}
