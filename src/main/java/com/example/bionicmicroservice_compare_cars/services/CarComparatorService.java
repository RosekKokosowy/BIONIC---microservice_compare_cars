package com.example.bionicmicroservice_compare_cars.services;

import com.example.bionicmicroservice_compare_cars.data.Car;
import com.example.bionicmicroservice_compare_cars.data.ComparisonDto;
import com.example.bionicmicroservice_compare_cars.data.ParametersWeight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
@Slf4j
public class CarComparatorService {
    private final int NUMBER_OF_PARAMS = 4;

    private double [] min = new double[NUMBER_OF_PARAMS];
    private double [] max = new double[NUMBER_OF_PARAMS];
    private int [] inv = new int[NUMBER_OF_PARAMS];

    Car car_1;
    Car car_2;

    ParametersWeight weights;

    Car car_1_normalized = new Car();
    Car car_2_normalized = new Car();

    private double  car1_result;
    private double  car2_result;

    //protects from normalization and calculation before initialization
    private boolean init_block = false;
    private boolean normal_block = false;
    private boolean boundaries_block = false;

    //bob the builder
    //data required by the constructor: (car1 params type: double [], car2 params type: double [], weights type: double [],
    //                                   min values for params type: double [], max values for params type: double [],
    //                                   indexes of inverted parameters type: double [] *-1 if there are none*,
    //                                   car1 wheel drive type: String *available: awd, rwd, fwd*, car2 wheel drive
    //                                   type: String, car1 fuel type: String *available: petrol, diesel, electric,
    //                                   gas, hydrogen. , 3 strings: preferred fuel, wheels and gearbox type: ArrayList<String>)
    public void setObject(ComparisonDto carsInfo) {
        log.info(carsInfo.toString());

        car_1 = carsInfo.getCars().get(0);
        car_2 = carsInfo.getCars().get(1);

        weights = carsInfo.getParametersWeight();

        init_block = true;

        set_boundaries();

        try {
            check_integrity();
        }catch (Exception e) {
            e.printStackTrace();
        }

        swap_boundaries();
        normalize();
    }

    //function returns the prefered car (1 or 2)
    public int PerformAnalisis()
    {
        calculate_preference();

        return car1_result > car2_result ? 0 : 1;
    }

    private void swap_boundaries()
    {
        if(boundaries_block)
        {
            for(int i : inv)
            {
                double temp = min[i];
                min[i] = max[i];
                max[i] = temp;
            }
        }
    }

    //initializes the min and max values for each car trait
    public void set_boundaries()
    {
        //yearOfManufacture
        min[0] = 1950;
        max[0] = Year.now().getValue();

        //mileage
        min[1] = 0;
        max[1] = 1000000;

        //price
        min[2] = 0;
        max[2] = 5000000;

        //horsePower
        min[3] = 0;
        max[3] = 1500;

        //inverse values (higher == worse)
        inv[0] = 1; //mileage
        inv[1] = 2; //price

        boundaries_block = true;
    }

    //determines which car has higher preference, performed last
    private void calculate_preference()
    {
        car1_result = 0;
        car2_result = 0;

        car1_result += car_1_normalized.getYearOfManufacture() * weights.getYearOfManufacture();
        car2_result += car_2_normalized.getYearOfManufacture() * weights.getYearOfManufacture();

        car1_result += car_1_normalized.getMileage() * weights.getMileage();
        car2_result += car_2_normalized.getMileage() * weights.getMileage();

        car1_result += car_1_normalized.getPrice() * weights.getPrice();
        car2_result += car_2_normalized.getPrice() * weights.getPrice();

        car1_result += car_1_normalized.getHorsePower() * weights.getHorsePower();
        car2_result += car_2_normalized.getHorsePower() * weights.getHorsePower();

        double average_1 = car1_result/NUMBER_OF_PARAMS;
        double average_2 = car2_result/NUMBER_OF_PARAMS;

        switch(car_1.getTypeOfFuel().toLowerCase()){
            case "diesel":
                if(weights.getTypeOfFuel() > 0.5 && weights.getTypeOfFuel() <= 0.75)
                    car1_result += average_1;
                break;
            case "gas":
                if(weights.getTypeOfFuel() > 0.2 && weights.getTypeOfFuel() <= 0.5)
                    car1_result += average_1;
                break;
            case "petrol":
                if(weights.getTypeOfFuel() > 0.75)
                    car1_result += average_1;
                break;
            case "electric":
                if(weights.getTypeOfFuel() >= 0 && weights.getTypeOfFuel() <= 0.2)
                    car1_result += average_1;
        }

        switch(car_2.getTypeOfFuel().toLowerCase()){
            case "diesel":
                if(weights.getTypeOfFuel() > 0.5 && weights.getTypeOfFuel() <= 0.75)
                    car2_result += average_2;
                break;
            case "gas":
                if(weights.getTypeOfFuel() > 0.2 && weights.getTypeOfFuel() <= 0.5)
                    car2_result += average_2;
                break;
            case "petrol":
                if(weights.getTypeOfFuel() > 0.75)
                    car2_result += average_2;
                break;
            case "electric":
                if(weights.getTypeOfFuel() >= 0 && weights.getTypeOfFuel() <= 0.2)
                    car2_result += average_2;
        }

        switch(car_1.getGearBox().toLowerCase()){
            case "manual":
                if(weights.getGearBox() > 0.5)
                    car1_result += average_1;
                break;
            case "automatic":
                if(weights.getGearBox() <= 0.5)
                    car1_result += average_1;
                break;
        }

        switch(car_2.getGearBox().toLowerCase()){
            case "manual":
                if(weights.getGearBox() > 0.5)
                    car2_result += average_2;
                break;
            case "automatic":
                if(weights.getGearBox() <= 0.5)
                    car2_result += average_2;
                break;
        }
    }

    //normalizes the values to range 0-1
    public void normalize()
    {
        if(init_block && boundaries_block)
        {
            car_1_normalized.setYearOfManufacture((car_1.getYearOfManufacture() - min[0])/(max[0] - min[0]));
            car_2_normalized.setYearOfManufacture((car_2.getYearOfManufacture() - min[0])/(max[0] - min[0]));

            car_1_normalized.setMileage((car_1.getMileage() - min[1])/(max[1] - min[1]));
            car_2_normalized.setMileage((car_2.getMileage() - min[1])/(max[1] - min[1]));

            car_1_normalized.setPrice((car_1.getPrice() - min[2])/(max[2] - min[2]));
            car_2_normalized.setPrice((car_2.getPrice() - min[2])/(max[2] - min[2]));

            car_1_normalized.setHorsePower((car_1.getHorsePower() - min[3])/(max[3] - min[3]));
            car_2_normalized.setHorsePower((car_2.getHorsePower() - min[3])/(max[3] - min[3]));

            normal_block = true;
        }
    }

    //makes sure the data given is acceptable
    private void check_integrity() throws Exception {
//        int baseline = car_1.length;
//
//        if(car_2.length != baseline || weights.length != baseline)
//            throw new Exception("mismatched trait and weight array lengths: \ncar1 " + baseline + "\ncar2 " + car_2.length
//                    + "\nweights " + weights.length);
//
//        if(max_value.length != baseline || min_value.length != baseline)
//            throw new Exception("mismatched boundaries lengths: \ncar1 " + baseline + "\nmax " + max_value.length
//                    + "\nmin " + min_value.length);
//
//        for(int i = 0 ; i < baseline ; i++)
//        {
//            if(car_1[i] < min_value[i] || car_1[i] > max_value[i])
//                throw new Exception("mismatched limits and values:\ncar1: " + car_1[i] + "\nmin: " + min_value[i]
//                        + "\nmax: " + max_value[i]);
//            if(car_2[i] < min_value[i] || car_2[i] > max_value[i])
//                throw new Exception("mismatched limits and values:\ncar2: " + car_2[i] + "\nmin: " + min_value[i]
//                        + "\nmax: " + max_value[i]);
//        }
//
//        for(int i = 0 ; i < max_value.length ; i++)
//        {
//            if(max_value[i] == min_value[i])  throw new Exception("max_value = min_value at index: " + i + " Cannot divide by zero!");
//        }

        boolean condition = false;

    }
}