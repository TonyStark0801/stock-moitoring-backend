package com.shubham.stockmonitoring.gateway.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Healthcheckdto {
    String name;
    String status;
}
