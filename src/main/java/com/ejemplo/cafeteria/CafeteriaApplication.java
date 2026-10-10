package com.ejemplo.cafeteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication: Anotación principal que habilita la configuración automática de Spring Boot,
// escaneo de componentes y configuración de la aplicación. Marca esta clase como punto de entrada.
@SpringBootApplication
public class CafeteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeteriaApplication.class, args);
    }
}