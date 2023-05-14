package com.example.bionicmicroservice_compare_cars.data;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class ParametersWeight {
    private double yearOfManufacture;
    private double mileage;
    private double price;
    private double horsePower;
    private double typeOfFuel;
    private double gearBox;

    public ParametersWeight(double i, double i1, double i2, double i3, double i4, double i5) {
        this.yearOfManufacture = i;
        this.mileage = i1;
        this.price = i2;
        this.horsePower = i3;
        this.typeOfFuel = i4;
        this.gearBox = i5;
    }

    public int getNumParams()
    {
        int res = 0;
        for(Field f : ParametersWeight.class.getDeclaredFields())
        {
            res++;
        }
        return res;
    }
}
