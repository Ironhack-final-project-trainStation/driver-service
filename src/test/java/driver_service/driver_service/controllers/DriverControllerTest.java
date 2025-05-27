package driver_service.driver_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import driver_service.driver_service.dtos.TrainDTO;
import driver_service.driver_service.exceptions.DriverNotFoundException;
import driver_service.driver_service.feignclients.TrainFeignClient;
import driver_service.driver_service.models.Driver;
import driver_service.driver_service.service.DriverService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainFeignClient trainFeignClient;

    @MockBean
    private DriverService driverService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDriverById_ReturnsAlsoTrain() throws Exception {
        Driver mockDriver = new Driver(6L, "Pedro", 58000 , "train1");
        TrainDTO mockTrain = new TrainDTO("train1", "Cordoba");

        Mockito.when(driverService.findDriverById(6L)).thenReturn(mockDriver);
        Mockito.when(trainFeignClient.getTrainById("train1")).thenReturn(mockTrain);

        mockMvc.perform(get("/api/driver/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Train.destination").value("Cordoba"))
                .andExpect(jsonPath("$.Driver.name").value("Pedro"));


    }

    @Test
    void testGetDriverById_NotFound() throws Exception {
        Mockito.when(driverService.findDriverById(6L))
                .thenThrow(new DriverNotFoundException("Driver not found"));

        mockMvc.perform(get("/api/driver/6"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Driver not found"));
    }

    @Test
    void testGetDriverByTrainId_AlsoReturnsTrain() throws Exception {
        Driver mockDriver = new Driver(6L, "Pedro", 58000 , "train1");
        TrainDTO mockTrain = new TrainDTO("train1", "Cordoba");

        Mockito.when(driverService.findByTrainId("train1")).thenReturn(mockDriver);
        Mockito.when(trainFeignClient.getTrainById("train1")).thenReturn(mockTrain);

        mockMvc.perform(get("/api/driver/train/train1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Train.destination").value("Cordoba"))
                .andExpect(jsonPath("$.Driver.name").value("Pedro"));


    }
}
