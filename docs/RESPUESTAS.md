# Respuestas HTTP - Café Andino

## Etapa 1

### GET /bienvenida

- Código HTTP: 200
- Content-Type: text/plain
- Body: `Bienvenido a Café Andino`

## Etapa 2

### GET /producto/{producto}

- Código HTTP: 200
- Content-Type: text/plain
- Body (ejemplo con tinto): `Preparando tu tinto...`

## Etapa 3

### GET /menu

- Código HTTP: 200
- Content-Type: application/json
- Body:

```json
{"pan de bono": 2500, "capuchino": 6500, "chocolate": 5500, "tinto": 3000}
```

- Nota: los precios son números sin comillas (int). El orden de las claves puede variar porque `Map.of` no garantiza orden.

### GET /cafeteria

- Código HTTP: 200
- Content-Type: application/json
- Body:

```json
{"horario": "7:00 a.m. - 7:00 p.m.", "abierto": true, "nombre": "Café Andino", "ciudad": "Bogotá"}
```

- Nota: `abierto` es booleano `true`, no el string `"true"`.

## Etapa 4

### GET /total/{precio}/{cantidad}

- Código HTTP: 200
- Content-Type: text/plain
- Body (6500/3): `Total a pagar: $19500`
- Body (3000/2): `Total a pagar: $6000`

### Experimento: GET /total/abc/3

- Código HTTP: **400 Bad Request**
- Excepción en la consola: `MethodArgumentTypeMismatchException` — `Failed to convert value of type 'java.lang.String' to required type 'int'; For input string: "abc"`

**Respuesta al experimento:** Spring no puede convertir el texto `"abc"` a `int`, por lo que lanza `MethodArgumentTypeMismatchException` y responde con HTTP 400. Es un error del **cliente** (4xx) porque la solicitud envía un dato inválido; el servidor no puede procesarlo y no es un fallo del lado del servidor (5xx).

## Etapa 5

### Cambio de puerto a 9091

Se modificó `src/main/resources/application.properties` para usar el puerto 9091. El puerto 8080 ya no responde.

### Tabla de pruebas en puerto 9091

| Petición (GET) | Código HTTP | Respuesta recibida |
|----------------|-------------|-------------------|
| /bienvenida | 200 | Bienvenido a Café Andino |
| /pedido/tinto | 404 | {"timestamp":"2026-10-10T20:49:23.345+00:00","status":404,"error":"Not Found","path":"/pedido/tinto"} |
| /menu | 200 | {"capuchino":6500,"pan de bono":2500,"tinto":3000,"chocolate":5500} |
| /cafeteria | 200 | {"abierto":true,"horario":"7:00 a.m. - 7:00 p.m.","ciudad":"Bogotá","nombre":"Café Andino"} |
| /total/3000/2 | 200 | Total a pagar: $6000 |
| /postres | 404 | {"timestamp":"2026-10-10T20:49:39.186+00:00","status":404,"error":"Not Found","path":"/postres"} |

## Etapa 6: preguntas de comprensión

### 1. ¿Qué necesita instalar un desarrollador para programar en Java?
**Respuesta:** b) El JDK  
**Justificación:** El JDK incluye el compilador (javac) y herramientas necesarias para desarrollar, mientras que el JRE solo ejecuta código compilado.

### 2. ¿Qué hace @GetMapping("/menu")?
**Respuesta:** b) Ejecuta el método cuando llega una petición GET a /menu  
**Justificación:** @GetMapping es una anotación de Spring que mapea peticiones HTTP GET a un método controlador específico.

### 3. ¿Qué código HTTP se obtiene al visitar una ruta que no existe?
**Respuesta:** b) 404  
**Justificación:** El código 404 Not Found indica que el servidor no encontró el recurso solicitado.

### 4. ¿En qué archivo se cambia el puerto?
**Respuesta:** c) application.properties  
**Justificación:** El puerto del servidor embebido se configura con la propiedad server.port en application.properties.

### 5. Explica con tus palabras qué hace @PathVariable
**Respuesta:** @PathVariable permite capturar partes variables de la URL y usarlas como parámetros en el método del controlador. En la ruta /pedido/{producto}, lo que el usuario escriba en lugar de {producto} (como "tinto" o "capuchino") se guarda automáticamente en una variable del método. Por ejemplo, si alguien entra a /pedido/tinto, el método recibe "tinto" como valor. Es como un espacio en blanco en la URL que se rellena dinámicamente.

### 6. ¿Qué librería convierte automáticamente un Map en JSON dentro de Spring Boot?
**Respuesta:** Jackson (com.fasterxml.jackson.core:jackson-databind)  
**Justificación:** Spring Boot incluye spring-boot-starter-json que trae Jackson, y el dependency:tree confirma la dependencia jackson-databind:2.15.4 que serializa Map a JSON automáticamente.

## Reto opcional

Nuevos endpoints agregados al mismo controlador (`CafeteriaController.java`), ejecutándose en el puerto **9091**.

### GET /descuento/{precio}/{porcentaje}

Fórmula: `precio final = precio - (precio * porcentaje / 100.0)`.  
La expresión `precio * porcentaje / 100.0` calcula el **valor del descuento** (por eso se usa `100.0` y no `100`, para evitar división entera en Java). El precio final se obtiene restando ese descuento al precio original.

| Petición (GET) | Código HTTP | Content-Type | Respuesta |
|----------------|-------------|--------------|-----------|
| `/descuento/10000/20` | 200 | text/plain;charset=UTF-8 | `Precio final: $8000.0` |
| `/descuento/5000/10` | 200 | text/plain;charset=UTF-8 | `Precio final: $4500.0` |

Comprobación: 10000 − (10000 × 20 / 100.0) = 10000 − 2000.0 = **8000.0**.  
5000 − (5000 × 10 / 100.0) = 5000 − 500.0 = **4500.0**.

### GET /resumen/{producto}/{cantidad}

Devuelve un JSON con las claves `producto` (string), `cantidad` (número) y `mensaje` (string).

| Petición (GET) | Código HTTP | Content-Type | Respuesta |
|----------------|-------------|--------------|-----------|
| `/resumen/capuchino/2` | 200 | application/json | `{"producto":"capuchino","mensaje":"Pedido de 2 capuchino registrado","cantidad":2}` |

El JSON es válido, `cantidad` viene como número (sin comillas) y el Content-Type es `application/json`.

### Verificación de endpoints anteriores (siguen funcionando)

| Petición (GET) | Código HTTP | Respuesta |
|----------------|-------------|-----------|
| `/bienvenida` | 200 | `Bienvenido a Café Andino` |
| `/producto/tinto` | 200 | `Preparando tu tinto...` |
| `/menu` | 200 | `{"tinto":3000,"pan de bono":2500,"capuchino":6500,"chocolate":5500}` |
| `/cafeteria` | 200 | `{"horario":"7:00 a.m. - 7:00 p.m.","abierto":true,"nombre":"Café Andino","ciudad":"Bogotá"}` |
| `/total/3000/2` | 200 | `Total a pagar: $6000` |

## Enlace del repositorio

El código fuente completo del proyecto está disponible en GitHub:

**https://github.com/sacal-dotcom/cafeteria-api**

El repositorio es público y contiene todas las etapas (1 a 6), el reto opcional y el README con la documentación de endpoints.