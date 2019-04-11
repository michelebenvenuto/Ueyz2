package com.example.ueyz2;

public class StoreModel {
    String description;
    float latitude;
    float longitude;
    String name;
    String type;
    int imageId;

    public StoreModel(String description, float latitude, float longitude, String name, String type, int imageId) {
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.type = type;
        this.imageId= imageId;
    }
}
