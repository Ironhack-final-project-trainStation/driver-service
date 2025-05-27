package driver_service.driver_service.service;

import driver_service.driver_service.dtos.DriverDTO;
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

    public DriverDTO findByTrainId(String trainID) throws DriverNotFoundException{
        Optional<Driver> driver = driverRepository.findByTrainId(trainID);
        if(driver.isPresent()) {
            Driver d =driver.get();
            return new DriverDTO(d.getId(), d.getName(), d.getTrainId());
        } else {
            throw  new DriverNotFoundException("Driver not found for train: " + trainID);
         }
    }


    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver (Long id, Driver updated) {
        Driver driver = findDriverById(id);
        driver.setName(updated.getName());
        driver.setSalary(updated.getSalary());
        driver.setTrainId(updated.getTrainId());
        return driverRepository.save(driver);
    }

    public void deleteDriver(Long id){
        Driver driver = findDriverById(id);
        driverRepository.delete(driver);
    }
}
