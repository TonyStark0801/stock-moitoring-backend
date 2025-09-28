package com.shubham.stockmonitoring.gateway.controller;

import com.shubham.stockmonitoring.commons.dto.BaseResponse;
import com.shubham.stockmonitoring.gateway.Dto.Healthcheckdto;
import com.shubham.stockmonitoring.gateway.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GatewayController  {

    private  final HealthCheckService healthCheckService;

    @Autowired
    public GatewayController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/health")
    public BaseResponse<List<Healthcheckdto>> healthCheck(){
        return healthCheckService.healthCheck();
    }

}
