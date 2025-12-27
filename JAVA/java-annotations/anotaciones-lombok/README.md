# 📦 Anotaciones Lombok | Reducción de Código Repetitivo

Lombok es una librería que automatiza la generación de código repetitivo (getters, setters, constructores, etc.) mediante anotaciones. **Dependencia:** `org.projectlombok:lombok`

---

## 🎯 Anotaciones Lombok Más Utilizadas

### **@Data** 🔑
**Cuándo usar:** En prácticamente todas las clases (entidades, DTOs, modelos).  
**Función:** Genera automáticamente getters, setters, toString(), equals() y hashCode().

```java
import lombok.Data;

@Data
public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    // Getters, setters, toString, equals, hashCode generados automáticamente
}
```

---

### **@Builder** 🏗️
**Cuándo usar:** Cuando necesitas construir objetos complejos de forma clara y legible.  
**Función:** Implementa el patrón Builder automáticamente.

```java
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
}

// Uso:
Producto producto = Producto.builder()
    .id(1L)
    .nombre("Laptop")
    .precio(999.99)
    .build();
```

---

### **@NoArgsConstructor** 🚫
**Cuándo usar:** Cuando JPA o frameworks requieren un constructor sin parámetros.  
**Función:** Genera constructor vacío automáticamente.

```java
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    // Constructor sin parámetros generado
}
```

---

### **@AllArgsConstructor** ✅
**Cuándo usar:** Cuando necesitas un constructor con todos los campos.  
**Función:** Genera constructor con todos los parámetros.

```java
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    // Constructor(Long id, String nombre, Double precio) generado
}
```

---

### **@RequiredArgsConstructor** 📌
**Cuándo usar:** Con inyección de dependencias, para campos final.  
**Función:** Genera constructor solo con campos marcados como `final`.

```java
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository; // final = requerido en constructor
    // Constructor(ProductoRepository repository) generado automáticamente
}
```

---

### **@Getter / @Setter** 📖
**Cuándo usar:** Cuando necesitas solo getters o solo setters en algunos campos.  
**Función:** Genera getters y/o setters de forma selectiva.

```java
import lombok.Getter;
import lombok.Setter;

public class Usuario {
    @Getter @Setter
    private String nombre;
    
    @Getter
    private String email; // Solo getter
}
```

---

### **@ToString** 🖨️
**Cuándo usar:** Cuando quieres personalizar la representación en texto del objeto.  
**Función:** Genera método toString() con opciones de exclusión.

```java
import lombok.ToString;
import lombok.Data;

@Data
@ToString(exclude = "password") // Excluye el campo password
public class Usuario {
    private String nombre;
    private String password;
}
```

---

### **@EqualsAndHashCode** ⚖️
**Cuándo usar:** En entidades para comparaciones correctas.  
**Función:** Genera equals() y hashCode() basados en los campos.

```java
import lombok.EqualsAndHashCode;
import lombok.Data;

@Data
@EqualsAndHashCode(exclude = "id") // Excluye id de la comparación
public class Producto {
    private Long id;
    private String nombre;
}
```

---

### **@Slf4j** 📝
**Cuándo usar:** En servicios, controladores y clases que necesitan logging.  
**Función:** Inyecta automáticamente un logger SLF4J.

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductoService {
    public void crearProducto(Producto p) {
        log.info("Creando producto: {}", p.getNombre());
        log.debug("Detalles: {}", p);
    }
}
```

---

## 📋 Tabla Rápida de Anotaciones Lombok

| Anotación | Genera | Dónde Usar |
| :--- | :--- | :--- |
| `@Data` | Getters, setters, toString, equals, hashCode | Clases de datos |
| `@Builder` | Patrón Builder | Objetos complejos |
| `@NoArgsConstructor` | Constructor vacío | Entidades JPA |
| `@AllArgsConstructor` | Constructor con todos los campos | Modelos |
| `@RequiredArgsConstructor` | Constructor con campos final | Servicios con inyección |
| `@Getter` | Getters | Campos específicos |
| `@Setter` | Setters | Campos específicos |
| `@ToString` | método toString() | Debugging |
| `@EqualsAndHashCode` | equals() y hashCode() | Comparaciones |
| `@Slf4j` | Logger SLF4J | Logging |

---

## ⚡ Ejemplo Completo

```java
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private Double precio;
    
    public void crear() {
        log.info("Producto creado: {}", this.nombre);
    }
}
```

---
[Volver a Guía Completa de Anotaciones](../README.md)