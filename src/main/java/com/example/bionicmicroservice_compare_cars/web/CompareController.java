package com.example.bionicmicroservice_compare_cars.web;

import com.example.bionicmicroservice_compare_cars.data.CarParameters;
import com.example.bionicmicroservice_compare_cars.data.CarsInfo;
import com.example.bionicmicroservice_compare_cars.services.CarComparatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/car/compare/comparison")
public class CompareController {

    CarComparatorService carComparatorService;

    @Autowired
    public CompareController(CarComparatorService carComparatorService) {
        this.carComparatorService = carComparatorService;
    }

    @PostMapping
    public ResponseEntity<CarParameters> processData(@RequestBody CarsInfo carsInfo) {
        carComparatorService.setObject(
                carsInfo
        );
        int res = carComparatorService.PerformAnalisis();
        log.info("result " + res);
        log.info(String.valueOf(carsInfo.getCarsParameters().get(res)));
        return ResponseEntity.ok(carsInfo.getCarsParameters().get(res));
    }
}
