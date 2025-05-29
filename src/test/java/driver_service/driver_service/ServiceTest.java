package driver_service.driver_service;

import driver_service.driver_service.exceptions.DriverNotFoundException;
import driver_service.driver_service.models.Driver;
import driver_service.driver_service.repositories.DriverRepository;
import driver_service.driver_service.service.DriverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    void testFindDriverById_ReturnsDriver() {
        Driver driver = new Driver(1L, "Pedro", 50000, "train1");
        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        Driver result = driverService.findDriverById(1L);

        assertNotNull(result);
        assertEquals("Pedro", result.getName());
        assertEquals("train1", result.getTrainId());
    }

    @Test
    void testFindDriverById_NotFound() {
        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> {
            driverService.findDriverById(1L);
        });
    }

    @Test
    void testSaveDriver() {
        Driver newDriver = new Driver(null, "Ana", 30000, "train2");
        Driver savedDriver = new Driver(2L, "Ana", 30000, "train2");

        Mockito.when(driverRepository.save(newDriver)).thenReturn(savedDriver);

        Driver result = driverService.saveDriver(newDriver);

        assertNotNull(result);
        assertEquals("Ana", result.getName());
        assertEquals("train2", result.getTrainId());
    }

    @Test
    void testDeleteDriver() {
        Long idToDelete = 3L;
        Driver testDriver = new Driver(idToDelete, "Ana", 30000, "train2");

        Mockito.when(driverRepository.findById(idToDelete)).thenReturn(Optional.of(testDriver));
        Mockito.doNothing().when(driverRepository).delete(testDriver);

        assertDoesNotThrow(() -> driverService.deleteDriver(idToDelete));
    }

    @Test
    void testDeleteDriver_NotFound() {
        Mockito.when(driverRepository.findById(85L)).thenReturn(Optional.empty());
        assertThrows(DriverNotFoundException.class, () -> driverService.deleteDriver(85L));
    }
}
