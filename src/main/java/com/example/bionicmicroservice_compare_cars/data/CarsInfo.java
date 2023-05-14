package com.example.bionicmicroservice_compare_cars.data;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CarsInfo {
    ArrayList<CarParameters> carsParameters;
    ParametersWeight parametersWeight;
}