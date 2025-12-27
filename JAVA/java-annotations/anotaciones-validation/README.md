# ✅ Anotaciones de Validación | Validar Datos de Entrada

Las anotaciones de validación garantizan que los datos cumplan reglas específicas antes de ser procesados. **Dependencia:** `jakarta.validation:jakarta.validation-api` (o `javax.validation:validation-api`)

---

## 🎯 Anotaciones de Validación Más Utilizadas

### **@NotNull** 🚫
**Cuándo usar:** En campos que no pueden ser null.  
**Función:** Valida que el valor no sea null.

```java
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoDTO {
    @NotNull(message = "El ID no puede ser nulo")
    private Long id;
    
    private String nombre;
}
```

---

### **@NotBlank** 📝
**Cuándo usar:** En strings (nombres, emails, descripciones).  
**Función:** Valida que no esté vacío ni contenga solo espacios.

```java
import jakarta.validation.constraints.NotBlank;

@Data
public class UsuarioDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El email es obligatorio")
    private String email;
}
```

---

### **@NotEmpty** 🎒
**Cuándo usar:** En colecciones, arrays o strings.  
**Función:** Valida que no esté vacío (pero permite null).

```java
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PedidoDTO {
    @NotEmpty(message = "Debe haber al menos un producto")
    private List<ProductoDTO> productos;
}
```

---

### **@Email** 📧
**Cuándo usar:** En campos de email.  
**Función:** Valida formato de correo electrónico.

```java
import jakarta.validation.constraints.Email;

@Data
public class ContactoDTO {
    @Email(message = "Email inválido")
    private String correo;
}
```

---

### **@Min / @Max** 📊
**Cuándo usar:** En valores numéricos (edad, precio, cantidad).  
**Función:** Valida valores mínimos y máximos.

```java
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Data
public class ProductoDTO {
    @Min(value = 0, message = "El precio mínimo es 0")
    @Max(value = 1000000, message = "El precio máximo es 1,000,000")
    private Double precio;
    
    @Min(value = 1, message = "Edad mínima: 1")
    @Max(value = 150, message = "Edad máxima: 150")
    private Integer edad;
}
```

---

### **@Size** 📏
**Cuándo usar:** En strings, colecciones o arrays.  
**Función:** Valida tamaño/longitud.

```java
import jakarta.validation.constraints.Size;

@Data
public class UsuarioDTO {
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;
    
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    private String password;
}
```

---

### **@Pattern** 🔤
**Cuándo usar:** Cuando necesitas validar con expresiones regulares (teléfono, código postal).  
**Función:** Valida contra un patrón regex.

```java
import jakarta.validation.constraints.Pattern;

@Data
public class ContactoDTO {
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos")
    private String telefono;
    
    @Pattern(regexp = "^[A-Z]{2}[0-9]{5}$", message = "Código postal inválido")
    private String codigoPostal;
}
```

---

### **@Valid** ✔️
**Cuándo usar:** En parámetros de controladores para validar objetos anidados.  
**Función:** Valida objetos complejos y sus propiedades.

```java
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    @PostMapping
    public ResponseEntity<?> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        // Si ProductoDTO no es válido, Spring lanza excepción automáticamente
        return ResponseEntity.ok("Producto creado");
    }
}
```

---

### **@Positive / @Negative** ➕➖
**Cuándo usar:** En números que deben ser positivos o negativos.  
**Función:** Valida que el número sea positivo (>0) o negativo (<0).

```java
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Negative;

@Data
public class TransaccionDTO {
    @Positive(message = "El monto debe ser positivo")
    private Double monto;
    
    @Negative(message = "La penalización debe ser negativa")
    private Double penalizacion;
}
```

---

### **@Digits** 🔢
**Cuándo usar:** En campos numéricos con precisión específica.  
**Función:** Valida cantidad de dígitos antes y después del decimal.

```java
import jakarta.validation.constraints.Digits;

@Data
public class ProductoDTO {
    @Digits(integer = 5, fraction = 2, message = "Máximo 5 dígitos enteros y 2 decimales")
    private Double precio; // Ej: 9999.99
}
```

---

### **@DecimalMin / @DecimalMax** 💰
**Cuándo usar:** En valores decimales con precisión.  
**Función:** Valida valores mínimos y máximos en decimales.

```java
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

@Data
public class PrecioDTO {
    @DecimalMin(value = "0.01", message = "El precio mínimo es 0.01")
    @DecimalMax(value = "99999.99", message = "El precio máximo es 99999.99")
    private Double precio;
}
```

---

## 📋 Tabla Rápida de Validación

| Anotación | Valida | Ejemplo |
| :--- | :--- | :--- |
| `@NotNull` | No es null | `@NotNull private Long id;` |
| `@NotBlank` | No está vacío (strings) | `@NotBlank private String nombre;` |
| `@NotEmpty` | No está vacío (colecciones) | `@NotEmpty private List<?> items;` |
| `@Email` | Formato de email | `@Email private String correo;` |
| `@Min/@Max` | Valores numéricos | `@Min(1) @Max(100)` |
| `@Size` | Longitud/tamaño | `@Size(min=3, max=50)` |
| `@Pattern` | Expresión regular | `@Pattern(regexp="^[0-9]{10}$")` |
| `@Valid` | Objetos anidados | `@Valid @RequestBody DTO dto` |
| `@Positive/@Negative` | Positivo/negativo | `@Positive private Double monto;` |
| `@Digits` | Precisión numérica | `@Digits(integer=5, fraction=2)` |
| `@DecimalMin/@DecimalMax` | Rango decimal | `@DecimalMin("0.01")` |

---

## ⚡ Ejemplo Completo

```java
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioDTO {
    @NotNull(message = "ID requerido")
    private Long id;
    
    @NotBlank(message = "Nombre obligatorio")
    @Size(min = 3, max = 100, message = "Entre 3 y 100 caracteres")
    private String nombre;
    
    @Email(message = "Email inválido")
    @NotBlank(message = "Email obligatorio")
    private String email;
    
    @Size(min = 8, message = "Mínimo 8 caracteres")
    private String password;
    
    @Min(value = 18, message = "Mínimo 18 años")
    @Max(value = 120, message = "Máximo 120 años")
    private Integer edad;
    
    @DecimalMin("0.01")
    @DecimalMax("99999.99")
    private Double saldo;
}
```

---
[Volver a Guía Completa de Anotaciones](../README.md)