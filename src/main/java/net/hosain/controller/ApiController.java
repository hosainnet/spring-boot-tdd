package net.hosain.controller;

import net.hosain.service.ApiService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    private ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping("vehicles")
    public String getVehicles() {
        return apiService.getAllVehicles();
    }
}
