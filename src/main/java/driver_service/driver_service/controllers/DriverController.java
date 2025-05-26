package driver_service.driver_service.controllers;

import driver_service.driver_service.dtos.TrainDTO;
import driver_service.driver_service.exceptions.DriverNotFoundException;
import driver_service.driver_service.feignclients.TrainFeignClient;
import driver_service.driver_service.models.Driver;
import driver_service.driver_service.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @Autowired
    TrainFeignClient trainFeignClient;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable Long id) {
        try {
            Driver foundDriver = driverService.findDriverById(id);

            TrainDTO foundTrain = trainFeignClient.getTrainById(foundDriver.getTrainId());
            System.out.println(foundTrain);

            Map<String, Object> response = new HashMap<>();
            response.put("Driver", foundDriver);
            response.put("Train", foundTrain);

            return  new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DriverNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
