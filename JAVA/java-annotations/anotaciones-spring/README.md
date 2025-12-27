# 🔧 Anotaciones Spring Core | Gestión de Componentes e Inyección de Dependencias

Las anotaciones de Spring Core gestionan componentes, configuración e inyección de dependencias. **Dependencia:** `spring-context`

---

## 🎯 Anotaciones Spring Core Más Utilizadas

### **@Component** 🧩
**Cuándo usar:** En clases genéricas que son componentes reutilizables.  
**Función:** Marca una clase como componente administrado por Spring.

```java
import org.springframework.stereotype.Component;

@Component
public class MiComponente {
    // Spring registra automáticamente esta clase como un bean
}
```

---

### **@Service** 💼
**Cuándo usar:** En clases que contienen lógica de negocio.  
**Función:** Marca una clase como servicio (especialización de @Component).

```java
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    // Contiene lógica de negocio relacionada con productos
    
    public void crearProducto(String nombre) {
        // Lógica de creación
    }
}
```

---

### **@Repository** 🗄️
**Cuándo usar:** En clases que acceden a la base de datos.  
**Función:** Marca como repositorio (acceso a datos), también traduce excepciones de BD.

```java
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Spring configura automáticamente el acceso a datos
}
```

---

### **@Controller** 👨‍💻
**Cuándo usar:** En clases que manejan solicitudes MVC (devuelven vistas HTML).  
**Función:** Marca como controlador que devuelve vistas.

```java
import org.springframework.stereotype.Controller;

@Controller
public class ProductoController {
    @GetMapping("/productos")
    public String listar(Model model) {
        // Devuelve una vista HTML (productos.html)
        return "productos";
    }
}
```

---

### **@RestController** 🌐
**Cuándo usar:** En controladores que devuelven JSON/XML (APIs REST).  
**Función:** Combina @Controller + @ResponseBody (devuelve directamente objetos JSON).

```java
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {
    
    @GetMapping
    public List<Producto> listar() {
        // Devuelve JSON automáticamente
        return List.of(new Producto());
    }
}
```

---

### **@Configuration** ⚙️
**Cuándo usar:** En clases que definen beans y configuración personalizada.  
**Función:** Marca una clase como fuente de definiciones de beans.

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```

---

### **@Bean** 📦
**Cuándo usar:** En métodos dentro de @Configuration para definir beans personalizados.  
**Función:** Registra el valor devuelto como un bean administrado por Spring.

```java
@Configuration
public class AppConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

---

### **@Autowired** 💉
**Cuándo usar:** Para inyectar dependencias automáticamente.  
**Función:** Inyecta un bean coincidente (por tipo o nombre).

```java
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository repository;
    
    public void crear(Producto p) {
        repository.save(p);
    }
}
```

---

### **@Qualifier** 🎯
**Cuándo usar:** Cuando hay múltiples beans del mismo tipo y necesitas especificar cuál usar.  
**Función:** Especifica qué bean inyectar por nombre.

```java
@Service
public class ProductoService {
    
    @Autowired
    @Qualifier("productoDatabaseRepository")
    private ProductoRepository repository;
}
```

---

### **@Primary** ⭐
**Cuándo usar:** Para marcar un bean como prioritario cuando hay múltiples del mismo tipo.  
**Función:** Define el bean por defecto a inyectar.

```java
@Configuration
public class AppConfig {
    
    @Bean
    @Primary
    public DataSource primaryDataSource() {
        return new HikariDataSource();
    }
}
```

---

### **@Value** 📄
**Cuándo usar:** Para inyectar valores de propiedades (application.properties).  
**Función:** Inyecta valores del archivo de configuración.

```java
import org.springframework.beans.factory.annotation.Value;

@Service
public class ConfigService {
    
    @Value("${app.nombre}")
    private String nombreApp;
    
    @Value("${app.version:1.0.0}") // Con valor por defecto
    private String version;
}
```

**En application.properties:**
```properties
app.nombre=Mi Aplicación
app.version=2.0.0
```

---

### **@ConfigurationProperties** 🔐
**Cuándo usar:** Para mapear múltiples propiedades a una clase de configuración.  
**Función:** Mapea propiedades del archivo a atributos de clase.

```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.database")
public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    
    // Getters y Setters
}
```

**En application.properties:**
```properties
app.database.url=jdbc:mysql://localhost:3306/db
app.database.username=root
app.database.password=123456
```

---

### **@Scope** 🔄
**Cuándo usar:** Para definir el ciclo de vida del bean (singleton, prototype, request, etc.).  
**Función:** Controla cuándo se crea el bean.

```java
import org.springframework.context.annotation.Scope;

@Component
@Scope("singleton") // Una sola instancia para toda la app
public class SingletonComponent {
}

@Component
@Scope("prototype") // Nueva instancia cada vez que se inyecta
public class PrototypeComponent {
}

@Component
@Scope("request") // Nueva instancia por solicitud HTTP
public class RequestComponent {
}
```

---

### **@Lazy** 😴
**Cuándo usar:** Para retrasar la inicialización de un bean hasta que se necesite.  
**Función:** No crea el bean al iniciar la aplicación.

```java
@Component
@Lazy
public class HeavyComponent {
    // No se instancia hasta que se inyecte por primera vez
}
```

---

### **@Conditional** ❓
**Cuándo usar:** Para crear beans condicionalmente según propiedades o ambiente.  
**Función:** Define condiciones para registrar beans.

```java
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Component
@ConditionalOnProperty(name = "feature.cache.enabled", havingValue = "true")
public class CacheManager {
    // Solo se crea si feature.cache.enabled=true
}
```

---

## 📋 Tabla Rápida de Anotaciones Spring Core

| Anotación | Función | Dónde Usar |
| :--- | :--- | :--- |
| `@Component` | Componente genérico | Clase |
| `@Service` | Lógica de negocio | Clase |
| `@Repository` | Acceso a datos | Clase |
| `@Controller` | Devuelve vistas | Clase |
| `@RestController` | Devuelve JSON/XML | Clase |
| `@Configuration` | Define beans | Clase |
| `@Bean` | Define bean personalizado | Método |
| `@Autowired` | Inyecta dependencia | Campo/Método |
| `@Qualifier` | Especifica bean | Campo |
| `@Primary` | Bean por defecto | Método/@Bean |
| `@Value` | Inyecta propiedad | Campo |
| `@ConfigurationProperties` | Mapea propiedades | Clase |
| `@Scope` | Ciclo de vida | Clase |
| `@Lazy` | Inicialización retrasada | Clase |
| `@Conditional` | Registro condicional | Clase |

---

## ⚡ Ejemplo Completo

```java
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository repository;
    
    @Value("${app.max.productos:100}")
    private Integer maxProductos;
    
    public List<Producto> listar() {
        return repository.findAll();
    }
    
    public void crear(Producto p) {
        repository.save(p);
    }
}

@Configuration
public class AppConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```

---
[Volver a Guía Completa de Anotaciones](../README.md)