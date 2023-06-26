package com.example.bionicmicroservice_compare_cars.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private Long id;
    private String name;
    private String img;
    private double yearOfManufacture;
    private double mileage;
    private double price;
    private double horsePower;
    private String typeOfFuel;
    private String gearBox;

//    public Car(int id, double yearOfManufacture, double mileage, double price, double horsePower, String typeOfFuel, String gearBox) {
//        this.id = id;
//        this.yearOfManufacture = yearOfManufacture;
//        this.mileage = mileage;
//        this.price = price;
//        this.horsePower = horsePower;
//        this.typeOfFuel = typeOfFuel;
//        this.gearBox = gearBox;
//    }
//
//    public Car() {
//
//    }
}
