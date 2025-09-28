package com.shubham.stockmonitoring.masterdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.shubham.stockmonitoring.masterdata",
    "com.shubham.stockmonitoring.commons"
})
public class MasterDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterDataServiceApplication.class, args);
	}

}
