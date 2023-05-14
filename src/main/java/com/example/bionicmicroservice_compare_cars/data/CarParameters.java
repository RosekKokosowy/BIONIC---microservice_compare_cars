package com.example.bionicmicroservice_compare_cars.data;

import lombok.Data;

@Data
public class CarParameters {

    private int id;
    private double yearOfManufacture;
    private double mileage;
    private double price;
    private double horsePower;
    private String typeOfFuel;
    private String gearBox;

    public CarParameters(int id, double yearOfManufacture, double mileage, double price, double horsePower, String typeOfFuel, String gearBox) {
        this.id = id;
        this.yearOfManufacture = yearOfManufacture;
        this.mileage = mileage;
        this.price = price;
        this.horsePower = horsePower;
        this.typeOfFuel = typeOfFuel;
        this.gearBox = gearBox;
    }

    public CarParameters() {

    }
}
