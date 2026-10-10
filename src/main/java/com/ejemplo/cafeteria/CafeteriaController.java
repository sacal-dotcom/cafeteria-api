package com.ejemplo.cafeteria;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// @RestController: Marca la clase como controlador REST. Combina @Controller y @ResponseBody,
// indicando que los métodos devuelven directamente el cuerpo de la respuesta HTTP (JSON, texto, etc.)
@RestController
public class CafeteriaController {

    // @GetMapping("/bienvenida"): Mapea peticiones HTTP GET a la ruta /bienvenida.
    // Cuando un cliente hace GET /bienvenida, se ejecuta este método y su retorno va al body de la respuesta.
    @GetMapping("/bienvenida")
    public String bienvenida() {
        return "Bienvenido a Café Andino";
    }

    // @GetMapping("/producto/{producto}"): Mapea peticiones GET a /producto/{producto}.
    // La parte {producto} es una variable de ruta que se captura con @PathVariable.
@GetMapping("/pedido/{producto}")
    public String producto(@PathVariable String producto) {
        // @PathVariable: Vincula el valor de la variable de ruta {producto} al parámetro del método.
        // El nombre en llaves ({producto}) debe coincidir con el nombre del parámetro (producto).
        return "Preparando tu " + producto + "...";
    }

    // @GetMapping("/menu"): Devuelve un JSON con los productos y precios de la cafetería.
    // Se construye con Map.of(...) que admite máximo 10 pares clave-valor.
    // Map.of no garantiza el orden de las claves, así que puede variar entre ejecuciones.
    // Spring convierte el Map a JSON automáticamente (con Jackson).
    @GetMapping("/menu")
    public Map<String, Integer> menu() {
        return Map.of(
            "tinto", 3000,
            "capuchino", 6500,
            "chocolate", 5500,
            "pan de bono", 2500
        );
    }

    // @GetMapping("/cafeteria"): Devuelve un JSON con la información de la cafetería.
    // Usa Map<String, Object> para mezclar tipos de datos (String y boolean).
    @GetMapping("/cafeteria")
    public Map<String, Object> cafeteria() {
        return Map.of(
            "nombre", "Café Andino",
            "ciudad", "Bogotá",
            "abierto", true,
            "horario", "7:00 a.m. - 7:00 p.m."
        );
    }

// @GetMapping("/total/{precio}/{cantidad}): Mapea GET a /total/{precio}/{cantidad}.
    // Los parámetros son int porque Spring convierte automáticamente el texto de la URL a número.
    // Si el texto no es un número válido, Spring lanza MethodArgumentTypeMismatchException → HTTP 400.
    @GetMapping("/total/{precio}/{cantidad}")
    public String total(@PathVariable int precio, @PathVariable int cantidad) {
        return "Total a pagar: $" + (precio * cantidad);
    }

    // @GetMapping("/descuento/{precio}/{porcentaje}):
    // Calcula el precio final = precio - (precio * porcentaje / 100.0).
    // NOTA: La fórmula "precio * porcentaje / 100.0" da el VALOR DEL DESCUENTO.
    // El precio final se obtiene restando ese descuento al precio original.
    // Se usa 100.0 (double) para que Java no haga división entera.
    @GetMapping("/descuento/{precio}/{porcentaje}")
    public String descuento(@PathVariable int precio, @PathVariable int porcentaje) {
        double descuento = precio * porcentaje / 100.0; // valor del descuento
        double precioFinal = precio - descuento;
        return "Precio final: $" + precioFinal;
    }

    // @GetMapping("/resumen/{producto}/{cantidad}): Devuelve un JSON con las claves
    // "producto", "cantidad" (número) y "mensaje".
    // Spring convierte el Map a JSON automáticamente (Jackson).
    @GetMapping("/resumen/{producto}/{cantidad}")
    public Map<String, Object> resumen(@PathVariable String producto, @PathVariable int cantidad) {
        return Map.of(
            "producto", producto,
            "cantidad", cantidad,
            "mensaje", "Pedido de " + cantidad + " " + producto + " registrado"
        );
    }
}