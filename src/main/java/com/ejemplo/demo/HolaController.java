package com.ejemplo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HolaController {

    @GetMapping("/hola")
    public String hola() {
        return "¡Bienvenido a la API REST profesional con Spring Boot!";
    }

    @GetMapping("/saludo/{nombre}")
    public String saludo(@PathVariable String nombre) {
        return "¡Hola, " + nombre + "! Bienvenido a la API REST con Spring Boot.";
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("aplicacion", "api-spring-demo");
        metadata.put("version", "1.0.0-SNAPSHOT");
        metadata.put("framework", "Spring Boot 3.2.5");
        metadata.put("lenguaje", "Java 17");
        metadata.put("descripcion", "Proyecto académico Spring Boot REST API");
        metadata.put("estado", "ACTIVO");
        return metadata;
    }

    @GetMapping("/despedida/{nombre}")
    public String despedida(@PathVariable String nombre) {
        return "Hasta luego, " + nombre;
    }

    @GetMapping("/perfil")
    public Map<String, Object> perfil() {
        Map<String, Object> perfil = new HashMap<>();
        perfil.put("nombre", "Juan Pérez");
        perfil.put("ciudad", "Madrid");
        perfil.put("lenguajeFavorito", "Java");
        return perfil;
    }

    @GetMapping("/suma/{a}/{b}")
    public Map<String, Object> suma(@PathVariable int a, @PathVariable int b) {
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("operandoA", a);
        resultado.put("operandoB", b);
        resultado.put("suma", a + b);
        return resultado;
    }
}