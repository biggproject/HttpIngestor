package world.inetum.bigg.model;

public class KafkaMessage {

    private final String topic;
    private final String key;
    private final Object data;

    public KafkaMessage(String topic, String key, Object data) {
        this.topic = topic;
        this.key = key;
        this.data = data;
    }

    public String getTopic() {
        return topic;
    }

    public String getKey() {
        return key;
    }

    public Object getData() {
        return data;
    }
}
