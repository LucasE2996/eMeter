package emonitor.app.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import emonitor.app.domain.Client;
import emonitor.app.domain.Meter;
import emonitor.app.domain.Report;
import emonitor.app.domain.Watt;
import emonitor.app.wrapper.MeterWrapper;

import java.io.IOException;

public class MeterDeserializer extends JsonDeserializer<MeterWrapper> {
    @Override
    public MeterWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);

        final String channel_id = jsonNode.get("channel").get("id").asText();
        final String name = jsonNode.get("channel").get("name").asText();
        // position 0 of array feed is the voltage value in ThingSpeak
        final double current = Double.parseDouble(jsonNode.get("feed").get(0).get("field1").asText());
        // position 1 of array feed is the voltage value in ThingSpeak
        final double voltage = Double.parseDouble(jsonNode.get("feed").get(1).get("field1").asText());

        return new MeterWrapper(new Meter(name, channel_id, new Watt(current, voltage)));
    }
}
