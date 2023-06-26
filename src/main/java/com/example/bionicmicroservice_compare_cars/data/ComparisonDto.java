package com.example.bionicmicroservice_compare_cars.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonDto {
    private List<Car> cars;
    private ParametersWeight parametersWeight;
}