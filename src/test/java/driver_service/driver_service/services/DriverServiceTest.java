package driver_service.driver_service.services;

import driver_service.driver_service.models.Driver;
import driver_service.driver_service.service.DriverService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class DriverServiceTest {
    @Autowired
    DriverService driverService;

    @Test
    @DisplayName("The driver we receive is correct")
    public void getDriverById() {
        Driver foundDriver = driverService.findDriverById(1L);
        assertNotNull(foundDriver);
    }
}
