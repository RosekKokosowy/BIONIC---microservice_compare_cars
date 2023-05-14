package com.example.bionicmicroservice_compare_cars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestCommunication {

    private final RestTemplate restTemplate;
    private static final String TEST_PATH = "http://localhost:8080/test/communication/restTemplate/test1/send";

    @Autowired
    public TestCommunication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String receiveTest1Message() {
        return restTemplate.getForObject(
                TEST_PATH,
                String.class
        );
    }
}
