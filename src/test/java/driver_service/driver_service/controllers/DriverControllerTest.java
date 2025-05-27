package driver_service.driver_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import driver_service.driver_service.dtos.DriverDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
        DriverDTO mockDriver = new DriverDTO(6L, "Pedro", "train1");
        TrainDTO mockTrain = new TrainDTO("train1", "Cordoba");

        Mockito.when(driverService.findByTrainId("train1")).thenReturn(mockDriver);
        Mockito.when(trainFeignClient.getTrainById("train1")).thenReturn(mockTrain);

        mockMvc.perform(get("/api/driver/train/train1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Train.destination").value("Cordoba"))
                .andExpect(jsonPath("$.Driver.name").value("Pedro"));


    }
    @Test
    void testCreateDriver() throws Exception {
        Driver driver = new Driver(6L, "Pedro", 58000 , "train1");

        Mockito.when(driverService.saveDriver(Mockito.any())).thenReturn(driver);

        mockMvc.perform(post("/api/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driver)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pedro"))
                .andExpect(jsonPath("$.trainId").value("train1"));

    }
    @Test
    void testUpdateDriver() throws Exception {
        Driver updated = new Driver(6L, "Pedro", 58000 , "train1");

        Mockito.when(driverService.updateDriver(Mockito.eq(6L), Mockito.any())).thenReturn(updated);

        mockMvc.perform(put("/api/driver/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pedro"));

    }

    @Test
    void testDeleteDriver() throws Exception {
        Mockito.doNothing().when(driverService).deleteDriver(6L);
        mockMvc.perform(delete("/api/driver/6"))
                .andExpect(status().isOk())
                .andExpect(content().string("Driver deleted successfully"));
    }

}
