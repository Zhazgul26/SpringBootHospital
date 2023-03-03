package com.example.springboothospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootHospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHospitalApplication.class, args);
        double res = test();
        if(res != -1.0)
            System.out.println(res);

        double res2 = test();
        if(res != -1.0)
            System.out.println(res2);
    }
    public static double test(){
        double random = Math.random();
        return random > 0.5 ? random : -1.0;
    }

}
