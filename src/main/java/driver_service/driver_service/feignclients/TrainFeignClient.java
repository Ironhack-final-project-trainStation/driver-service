package driver_service.driver_service.feignclients;

import driver_service.driver_service.dtos.TrainDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "train-service")
public interface TrainFeignClient {

    @GetMapping("api/train/{id}")
    TrainDTO getTrainById(@PathVariable String id);
}
