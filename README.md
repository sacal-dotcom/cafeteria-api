# Café Andino — API REST

Proyecto académico de una API REST construida con **Spring Boot** que simula una cafetería andina. Incluye endpoints de texto, JSON, cálculo de totales, descuentos y resúmenes de pedidos.

## Requisitos

- **JDK 21** (o superior). El `pom.xml` está configurado con Spring Boot 3.2.5 y requiere Java 17+, pero se recomienda JDK 21 para el desarrollo actual.
- **Apache Maven** (incluye el wrapper `./mvnw` / `mvnw.cmd`).

## Cómo ejecutarlo

Abre una terminal en la raíz del proyecto y ejecuta:

```bash
./mvnw spring-boot:run
```

O, en Windows (PowerShell / CMD):

```bash
mvnw.cmd spring-boot:run
```

El servidor arranca en el **puerto 9091** (configurado en `src/main/resources/application.properties`). Una vez que veas `Tomcat started on port 9091`, la API está disponible en `http://localhost:9091`.

## Endpoints

Todas las rutas son `GET` y se acceden por `http://localhost:9091`.

### Texto

| Petición | Respuesta (200) |
|----------|-----------------|
| `GET /bienvenida` | `Bienvenido a Café Andino` |
| `GET /producto/{producto}` | `Preparando tu {producto}...` |

Ejemplo: `GET /producto/tinto` → `Preparando tu tinto...`

### JSON

| Petición | Respuesta (200, `application/json`) |
|----------|-------------------------------------|
| `GET /menu` | `{"tinto":3000,"pan de bono":2500,"capuchino":6500,"chocolate":5500}` |
| `GET /cafeteria` | `{"nombre":"Café Andino","ciudad":"Bogotá","abierto":true,"horario":"7:00 a.m. - 7:00 p.m."}` |
| `GET /resumen/{producto}/{cantidad}` | `{"producto":"capuchino","cantidad":2,"mensaje":" Pediatrics de 2 capuchino registrado"}` |

### Cálculos

| Petición | Respuesta (200, `text/plain`) |
|----------|-------------------------------|
| `GET /total/{precio}/{cantidad}` | `Total a pagar: $<precio * cantidad>` |
| `GET /descuento/{precio}/{porcentaje}` | `Precio final: $<precio - precio*porcentaje/100.0>` |

Ejemplos:
- `GET /total/3000/2` → `Total a pagar: $6000`
- `GET /descuento/10000/20` → `Precio final: $8000.0`
- `GET /descuento/5000/10` → `Precio final: $4500.0`

### Notas

- `/total/abc/3` devuelve **400 Bad Request** porque Spring no puede convertir `abc` a `int` (`MethodArgumentTypeMismatchException`).
- Cualquier ruta no definida devuelve **404 Not Found**.
- El orden de las claves en `/menu` y `/cafeteria` puede variar porque `Map.of` no garantiza orden.