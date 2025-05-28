package driver_service.driver_service.controllers;

import driver_service.driver_service.dtos.DriverDTO;
import driver_service.driver_service.dtos.TrainDTO;
import driver_service.driver_service.exceptions.DriverNotFoundException;
import driver_service.driver_service.feignclients.TrainFeignClient;
import driver_service.driver_service.models.Driver;
import driver_service.driver_service.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/train/{trainId}")
    public ResponseEntity<?> getDriverByTrainId(@PathVariable String trainId){
        try{
            DriverDTO driver = driverService.findByTrainId(trainId);

            return  new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (DriverNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver (@RequestBody Driver driver) {
        Driver newDriver = driverService.saveDriver(driver);
        return new ResponseEntity<>(newDriver, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable Long id, @RequestBody Driver driver) {
        try {
            Driver updatedDriver = driverService.updateDriver(id, driver);
            return new ResponseEntity<>(updatedDriver, HttpStatus.OK);
        } catch (DriverNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDriver (@PathVariable Long id) {
        try {
            driverService.deleteDriver(id);
            return new ResponseEntity<>("Driver deleted successfully", HttpStatus.OK);
        } catch (DriverNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
