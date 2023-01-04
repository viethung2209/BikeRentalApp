package com.hunglee.bikerentalapp.Models;

public class BikesModel {
    String bikeName, description;
    int price;

    public BikesModel(String bikeName, String description, int price) {
        this.bikeName = bikeName;
        this.description = description;
        this.price = price;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
