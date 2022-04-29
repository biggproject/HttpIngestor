package world.inetum.bigg.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import world.inetum.bigg.json.KeepAsJsonDeserializer;

public class HarmonizerInput {

    private String user;
    private String dataSourceName;
    private String collectionType;

    @JsonDeserialize(using = KeepAsJsonDeserializer.class)
    private String data;

    public HarmonizerInput() {}

    public void setUser(String user) {
        this.user = user;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUser() {
        return user;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "HarmonizerInput{" +
                ", user='" + user + '\'' +
                ", dataSourceName='" + dataSourceName + '\'' +
                ", collectionType='" + collectionType + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
