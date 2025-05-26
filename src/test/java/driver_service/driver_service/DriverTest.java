package driver_service.driver_service;

import driver_service.driver_service.models.Driver;
import driver_service.driver_service.repositories.DriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DriverTest {

    @Autowired
    DriverRepository driverRepository;

    @Test
    public void addDriver () {
        Driver testDriver = new Driver();
        testDriver.setName("Jose Luis Perez");
        testDriver.setSalary(35000);
        testDriver.setTrainId("AV88555");
        driverRepository.save(testDriver);
        System.out.println("New driver info : "+ testDriver);
        //driverRepository.delete(testDriver);

    }
}
