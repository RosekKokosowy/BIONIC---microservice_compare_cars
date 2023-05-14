package com.example.bionicmicroservice_compare_cars.web;
import com.example.bionicmicroservice_compare_cars.services.TestCommunication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final TestCommunication testCommunication;

    @Autowired
    public TestController(TestCommunication testCommunication) {
        this.testCommunication = testCommunication;
    }

    @GetMapping("/communication/restTemplate/test1/send")
    public String sendMessage(){
        try{
            log.info("TEST: sending packages!");
            return "TEST: Communication works!";
        }catch (Exception e){
            log.info("ERROR: sending packages has failed!");
            return "";
        }
    }

    @GetMapping("/communication/restTemplate/test1/receive")
    public String receiveMessage(){
        try{
            log.info("TEST: receiving packages!");
            return testCommunication.receiveTest1Message();
        }catch (Exception e){
            log.info("ERROR: receiving packages has failed!");
            return "";
        }
    }
}

