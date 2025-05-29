package driver_service.driver_service;

import driver_service.driver_service.exceptions.DriverNotFoundException;
import driver_service.driver_service.models.Driver;
import driver_service.driver_service.repositories.DriverRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class DriverTest {

    @Autowired
    DriverRepository driverRepository;

    @Test
    public void addDriverAndFindById () {
        Driver testDriver = new Driver();
        testDriver.setName("Jose Luis Perez");
        testDriver.setSalary(35000);
        testDriver.setTrainId("AV88555");
        driverRepository.save(testDriver);
        System.out.println("New driver info : "+ testDriver);

        Optional<Driver> found = driverRepository.findById(testDriver.getId());
        assertEquals("Jose Luis Perez", found.get().getName());
        driverRepository.delete(testDriver);

    }



}
