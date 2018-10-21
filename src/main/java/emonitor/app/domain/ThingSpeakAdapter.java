package emonitor.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingSpeakAdapter {
    private  Channel channel;
    private  List<Feed> feeds;

    public ThingSpeakAdapter(Channel channel, List<Feed> feeds) {
        this.channel = channel;
        this.feeds = feeds;
    }

    protected ThingSpeakAdapter() {}

    public Channel getChannel() {
        return channel;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public Integer getId() {
        return getChannel().getId();
    }

    public String getName() {
        return getChannel().getName();
    }

    public String getField1() {
        return getFeeds().get(0).getField1();
    }

    public String getField2() {
        return getFeeds().get(0).getField2();
    }
}

class Feed {
    private @Getter int entry_id;
    private @Getter String created_at;
    private @Getter String field1;
    private @Getter String field2;

    public Feed() { }
}

class Channel {
    private @Getter Integer id;
    private @Getter String name;
    private @Getter String latitude;
    private @Getter String logitude;
    private @Getter String field1;
    private @Getter String field2;
    private @Getter String created_at;
    private @Getter String updated_at;
    private @Getter Integer last_entry_id;

    public Channel () {}
}
