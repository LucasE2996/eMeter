package emonitor.app.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import emonitor.app.domain.User;
import emonitor.app.serialization.UserDeserializer;
import emonitor.app.serialization.UserSerializer;
import lombok.Getter;

@JsonSerialize(using = UserSerializer.class)
@JsonDeserialize(using = UserDeserializer.class)
public class UserWrapper {

    @Getter private final User user;

    public UserWrapper(final User user) {
        this.user = user;
    }
}
