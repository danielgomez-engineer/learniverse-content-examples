# 🌐 Anotaciones Spring Web | Controladores REST y Mapeo HTTP

Las anotaciones de Spring Web manejan controladores REST, mapeo de rutas y procesamiento de solicitudes HTTP. **Dependencia:** `spring-web`

---

## 🎯 Anotaciones Spring Web Más Utilizadas

### **@RequestMapping** 🗺️
**Cuándo usar:** Para mapear rutas en controladores.  
**Función:** Define ruta y métodos HTTP permitidos.

```java
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    // Todas las rutas bajo /api/productos
}
```

---

### **@GetMapping** 📥
**Cuándo usar:** En métodos que obtienen datos (método GET).  
**Función:** Mapea solicitudes GET a un método.

```java
@GetMapping
public List<Producto> listar() {
    return repository.findAll();
}

@GetMapping("/{id}")
public Producto obtener(@PathVariable Long id) {
    return repository.findById(id).orElse(null);
}
```

---

### **@PostMapping** 📤
**Cuándo usar:** En métodos que crean recursos (método POST).  
**Función:** Mapea solicitudes POST a un método.

```java
@PostMapping
public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
    Producto creado = repository.save(producto);
    return ResponseEntity.status(201).body(creado);
}
```

---

### **@PutMapping** 🔄
**Cuándo usar:** En métodos que actualizan recursos completos (método PUT).  
**Función:** Mapea solicitudes PUT a un método.

```java
@PutMapping("/{id}")
public ResponseEntity<Producto> actualizar(
    @PathVariable Long id,
    @RequestBody Producto producto) {
    
    Producto existente = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
    
    existente.setNombre(producto.getNombre());
    existente.setPrecio(producto.getPrecio());
    
    return ResponseEntity.ok(repository.save(existente));
}
```

---

### **@DeleteMapping** 🗑️
**Cuándo usar:** En métodos que eliminan recursos (método DELETE).  
**Función:** Mapea solicitudes DELETE a un método.

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build(); // 204 No Content
}
```

---

### **@PatchMapping** 🔧
**Cuándo usar:** En métodos que actualizan recursos parcialmente (método PATCH).  
**Función:** Mapea solicitudes PATCH a un método.

```java
@PatchMapping("/{id}")
public ResponseEntity<Producto> actualizarParcial(
    @PathVariable Long id,
    @RequestBody Map<String, Object> cambios) {
    
    Producto existente = repository.findById(id)
        .orElseThrow();
    
    cambios.forEach((clave, valor) -> {
        switch(clave) {
            case "nombre" -> existente.setNombre((String) valor);
            case "precio" -> existente.setPrecio((Double) valor);
        }
    });
    
    return ResponseEntity.ok(repository.save(existente));
}
```

---

### **@RequestBody** 📦
**Cuándo usar:** Para obtener el cuerpo de la solicitud en formato JSON/XML.  
**Función:** Convierte JSON/XML de la solicitud a objeto Java.

```java
@PostMapping
public ResponseEntity<?> crear(@RequestBody Producto producto) {
    // JSON -> Objeto Producto
    return ResponseEntity.ok(repository.save(producto));
}

// Solicitud HTTP:
// POST /api/productos
// Content-Type: application/json
// {"nombre": "Laptop", "precio": 999.99}
```

---

### **@ResponseBody** 📤
**Cuándo usar:** En controladores @Controller para devolver JSON directamente.  
**Función:** Convierte objeto Java a JSON/XML en la respuesta.

```java
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/productos")
public class ProductoController {
    
    @GetMapping
    @ResponseBody // Sin esto, Spring buscaría una vista HTML
    public List<Producto> listar() {
        return repository.findAll();
    }
}
```

---

### **@RequestParam** 🔍
**Cuándo usar:** Para obtener parámetros del query string.  
**Función:** Extrae parámetros de la URL.

```java
@GetMapping("/buscar")
public List<Producto> buscar(
    @RequestParam(name = "nombre") String nombre,
    @RequestParam(name = "precioMin", required = false) Double precioMin,
    @RequestParam(defaultValue = "10") Integer limite) {
    
    // GET /api/productos/buscar?nombre=Laptop&precioMin=500&limite=20
    return repository.buscar(nombre, precioMin, limite);
}
```

---

### **@PathVariable** 📍
**Cuándo usar:** Para obtener variables de la ruta (path).  
**Función:** Extrae variables definidas en la ruta.

```java
@GetMapping("/{id}/categorias/{categoriaId}")
public Producto obtener(
    @PathVariable Long id,
    @PathVariable Long categoriaId) {
    
    // GET /api/productos/5/categorias/3
    return repository.findByIdAndCategoriaId(id, categoriaId);
}
```

---

### **@RequestHeader** 🔐
**Cuándo usar:** Para obtener valores de headers HTTP.  
**Función:** Extrae valores de los headers de la solicitud.

```java
@GetMapping
public List<Producto> listar(
    @RequestHeader("Authorization") String token,
    @RequestHeader(value = "X-Custom-Header", required = false) String custom) {
    
    // Valida el token y procesa
    return repository.findAll();
}
```

---

### **@CrossOrigin** 🌍
**Cuándo usar:** Para habilitar CORS (Cross-Origin Resource Sharing).  
**Función:** Permite solicitudes desde otros dominios.

```java
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ProductoController {
    // Acepta solicitudes de http://localhost:3000
}
```

---

### **@ExceptionHandler** ⚠️
**Cuándo usar:** Para manejar excepciones específicas en controladores.  
**Función:** Captura excepciones y devuelve respuesta personalizada.

```java
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException e) {
        return new ErrorResponse("Recurso no encontrado", 404, e.getMessage());
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(IllegalArgumentException e) {
        return new ErrorResponse("Solicitud inválida", 400, e.getMessage());
    }
}
```

---

### **@ResponseStatus** 📊
**Cuándo usar:** Para definir el código HTTP de respuesta.  
**Función:** Especifica el código HTTP de la respuesta.

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED) // 201 Created
public Producto crear(@RequestBody Producto producto) {
    return repository.save(producto);
}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content
public void eliminar(@PathVariable Long id) {
    repository.deleteById(id);
}
```

---

### **@MatrixVariable** 🔗
**Cuándo usar:** Para variables de matriz en la URL.  
**Función:** Extrae variables de matriz (formato especial).

```java
@GetMapping("/buscar;nombre={nombre};precio={precio}")
public List<Producto> buscar(
    @MatrixVariable String nombre,
    @MatrixVariable Double precio) {
    
    // GET /api/productos/buscar;nombre=Laptop;precio=999
    return repository.buscar(nombre, precio);
}
```

---

### **@ControllerAdvice** 🛡️
**Cuándo usar:** Para crear manejador global de excepciones.  
**Función:** Define excepciones globales para toda la aplicación.

```java
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception e) {
        ErrorResponse error = new ErrorResponse("Error interno", 500, e.getMessage());
        return ResponseEntity.status(500).body(error);
    }
}
```

---

## 📋 Tabla Rápida de Anotaciones Spring Web

| Anotación | Función | Dónde Usar |
| :--- | :--- | :--- |
| `@RequestMapping` | Mapea rutas HTTP | Método/Clase |
| `@GetMapping` | Mapea GET | Método |
| `@PostMapping` | Mapea POST | Método |
| `@PutMapping` | Mapea PUT | Método |
| `@DeleteMapping` | Mapea DELETE | Método |
| `@PatchMapping` | Mapea PATCH | Método |
| `@RequestBody` | Convierte JSON a objeto | Parámetro |
| `@ResponseBody` | Convierte objeto a JSON | Método |
| `@RequestParam` | Parámetros de query string | Parámetro |
| `@PathVariable` | Variables de ruta | Parámetro |
| `@RequestHeader` | Headers de solicitud | Parámetro |
| `@CrossOrigin` | Habilita CORS | Método/Clase |
| `@ExceptionHandler` | Maneja excepciones | Método |
| `@ResponseStatus` | Código HTTP de respuesta | Método |
| `@MatrixVariable` | Variables de matriz | Parámetro |
| `@ControllerAdvice` | Manejador global | Clase |

---

## ⚡ Ejemplo Completo

```java
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductoController {
    
    private final ProductoService service;
    
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        return ResponseEntity.ok(service.listar(page, size));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO creado = service.crear(dto);
        return ResponseEntity.status(201).body(creado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(
        @PathVariable Long id,
        @Valid @RequestBody ProductoDTO dto) {
        
        return ResponseEntity.ok(service.actualizar(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(404)
            .body(new ErrorResponse("No encontrado", 404, e.getMessage()));
    }
}
```

---
[Volver a Guía Completa de Anotaciones](../README.md)