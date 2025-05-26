package driver_service.driver_service.service;

import driver_service.driver_service.exceptions.DriverNotFoundException;
import driver_service.driver_service.models.Driver;
import driver_service.driver_service.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    public Driver findDriverById(Long id) throws DriverNotFoundException{
        Optional<Driver> foundDriver = driverRepository.findById(id);

        if(foundDriver.isPresent()){
            return foundDriver.get();
        } else {
            throw new DriverNotFoundException("Driver not found");
        }
    }
}
