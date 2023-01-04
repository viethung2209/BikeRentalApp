package com.hunglee.bikerentalapp.Models;

public class BikeParkingModel {
    int image;
    String parkingName;
    int bikeNumber;
    String description;

    public BikeParkingModel(int image, String parkingName, int bikeNumber, String description) {
        this.image = image;
        this.parkingName = parkingName;
        this.bikeNumber = bikeNumber;
        this.description = description;
    }

    public BikeParkingModel() {

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return parkingName;
    }

    public void setName(String parkingName) {
        this.parkingName = parkingName;
    }

    public int getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(int bikeNumber) {
        this.bikeNumber = bikeNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
