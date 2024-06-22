package com.ecommerce.ne;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor

public class AdminApplication  {
   
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}



