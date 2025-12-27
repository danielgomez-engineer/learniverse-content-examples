# 💾 Anotaciones JPA | Persistencia y Mapeo de Base de Datos

Las anotaciones JPA (Jakarta Persistence API) mapean clases Java a tablas de base de datos. **Dependencia:** `jakarta.persistence:jakarta.persistence-api` (o `javax.persistence:javax.persistence-api`)

---

## 🎯 Anotaciones JPA Más Utilizadas

### **@Entity** 📋
**Cuándo usar:** En clases que representan tablas de base de datos.  
**Función:** Marca una clase como entidad JPA persistible.

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
    // Representa la tabla 'productos'
}
```

---

### **@Table** 🗂️
**Cuándo usar:** Junto con @Entity, para personalizar el nombre de la tabla.  
**Función:** Define el nombre y esquema de la tabla.

```java
@Entity
@Table(name = "producto", schema = "tienda", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre", "categoria"})
})
public class Producto {
    // Tabla: tienda.producto
}
```

---

### **@Id** 🔑
**Cuándo usar:** En el atributo que es la clave primaria.  
**Función:** Marca el campo como identificador único (clave primaria).

```java
import jakarta.persistence.Id;

@Entity
public class Producto {
    @Id
    private Long id;
    // Este campo es la clave primaria
}
```

---

### **@GeneratedValue** ⚙️
**Cuándo usar:** Con @Id, para auto-generar valores.  
**Función:** Define cómo generar automáticamente el valor de clave primaria.

```java
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ID se genera automáticamente por la BD (AUTO_INCREMENT)
}
```

**Estrategias comunes:**
- `IDENTITY`: AUTO_INCREMENT de la BD
- `SEQUENCE`: Usa secuencia de BD
- `UUID`: Genera UUID único
- `AUTO`: Elige automáticamente

---

### **@Column** 📊
**Cuándo usar:** Para personalizar propiedades de columnas.  
**Función:** Define propiedades de la columna (nullable, unique, length, etc.).

```java
import jakarta.persistence.Column;

@Entity
public class Producto {
    @Column(name = "nombre_producto", nullable = false, unique = true, length = 255)
    private String nombre;
    
    @Column(name = "precio", precision = 10, scale = 2)
    private Double precio;
}
```

**Propiedades comunes:**
- `name`: Nombre de la columna
- `nullable`: Permite null (false = NOT NULL)
- `unique`: Valor único
- `length`: Longitud máxima (strings)
- `precision` / `scale`: Precisión para decimales

---

### **@ManyToOne** 👥
**Cuándo usar:** En relaciones uno-a-muchos desde el lado "muchos".  
**Función:** Define relación muchos-a-uno con otra entidad.

```java
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Producto {
    @Id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    // Muchos productos pertenecen a una categoría
}
```

---

### **@OneToMany** 🔗
**Cuándo usar:** En relaciones uno-a-muchos desde el lado "uno".  
**Función:** Define relación uno-a-muchos con otra entidad.

```java
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Categoria {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Producto> productos;
    // Una categoría tiene muchos productos
}
```

---

### **@ManyToMany** 🔀
**Cuándo usar:** En relaciones muchos-a-muchos.  
**Función:** Define relación muchos-a-muchos (requiere tabla intermedia).

```java
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;

@Entity
public class Usuario {
    @Id
    private Long id;
    
    @ManyToMany
    @JoinTable(
        name = "usuario_rol",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;
}
```

---

### **@OneToOne** 💑
**Cuándo usar:** En relaciones uno-a-uno.  
**Función:** Define relación uno-a-uno entre entidades.

```java
import jakarta.persistence.OneToOne;

@Entity
public class Usuario {
    @Id
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
}
```

---

### **@JoinColumn** 🔗
**Cuándo usar:** En relaciones (@ManyToOne, @OneToOne) para personalizar la columna de relación.  
**Función:** Define el nombre y propiedades de la columna de relación.

```java
@Entity
public class Producto {
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_producto_categoria"))
    private Categoria categoria;
}
```

---

### **@JoinTable** 📊
**Cuándo usar:** En relaciones @ManyToMany para personalizar la tabla intermedia.  
**Función:** Define propiedades de la tabla intermedia.

```java
@ManyToMany
@JoinTable(
    name = "usuario_permiso",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "permiso_id")
)
private List<Permiso> permisos;
```

---

### **@CreationTimestamp** ⏰
**Cuándo usar:** Para registrar automáticamente la fecha de creación.  
**Función:** Guarda la fecha/hora de creación automáticamente. **Dependencia:** Hibernate

```java
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
public class Producto {
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
```

---

### **@UpdateTimestamp** 🔄
**Cuándo usar:** Para registrar automáticamente la fecha de última actualización.  
**Función:** Guarda la fecha/hora de última actualización. **Dependencia:** Hibernate

```java
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Producto {
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
```

---

### **@Transient** ➖
**Cuándo usar:** En campos que no deben persistirse en BD.  
**Función:** Excluye el campo del mapeo a base de datos.

```java
@Entity
public class Producto {
    @Id
    private Long id;
    
    @Transient
    private String codigoTemporal; // No se guarda en BD
}
```

---

### **@Enumerated** 🎯
**Cuándo usar:** En atributos que son enumeraciones.  
**Función:** Mapea enum a columna VARCHAR o INTEGER.

```java
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

public enum EstadoProducto {
    ACTIVO, INACTIVO, DESCONTINUADO
}

@Entity
public class Producto {
    @Enumerated(EnumType.STRING) // Guarda "ACTIVO", "INACTIVO", etc.
    private EstadoProducto estado;
}
```

---

### **@Lob** 📦
**Cuándo usar:** En campos de datos grandes (texto largo, imágenes, archivos).  
**Función:** Mapea a TEXT (Clob) o BLOB (Blob) en BD.

```java
import jakarta.persistence.Lob;

@Entity
public class Articulo {
    @Lob
    @Column(columnDefinition = "TEXT")
    private String descripcionLarga;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;
}
```

---

### **@Version** 🔢
**Cuándo usar:** Para optimistic locking (control de concurrencia).  
**Función:** Detecta cambios concurrentes automáticamente.

```java
import jakarta.persistence.Version;

@Entity
public class Producto {
    @Version
    private Long version;
    // Se incrementa cada vez que se actualiza
}
```

---

## 📋 Tabla Rápida de Anotaciones JPA

| Anotación | Función | Dónde Usar |
| :--- | :--- | :--- |
| `@Entity` | Define entidad persistible | Clase |
| `@Table` | Personaliza nombre de tabla | Clase |
| `@Id` | Clave primaria | Atributo |
| `@GeneratedValue` | Auto-genera ID | Atributo con @Id |
| `@Column` | Personaliza columna | Atributo |
| `@ManyToOne` | Relación muchos-a-uno | Atributo |
| `@OneToMany` | Relación uno-a-muchos | Atributo |
| `@ManyToMany` | Relación muchos-a-muchos | Atributo |
| `@OneToOne` | Relación uno-a-uno | Atributo |
| `@JoinColumn` | Personaliza columna de relación | Atributo |
| `@JoinTable` | Personaliza tabla intermedia | Atributo |
| `@CreationTimestamp` | Fecha de creación automática | Atributo |
| `@UpdateTimestamp` | Fecha de actualización automática | Atributo |
| `@Transient` | Excluye del mapeo | Atributo |
| `@Enumerated` | Mapea enum | Atributo |
| `@Lob` | Datos grandes | Atributo |
| `@Version` | Control de concurrencia | Atributo |

---

## ⚡ Ejemplo Completo

```java
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 255)
    private String nombre;
    
    @Column(precision = 10, scale = 2)
    private Double precio;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    @Enumerated(EnumType.STRING)
    private EstadoProducto estado;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Transient
    private String campotemporal;
}
```

---
[Volver a Guía Completa de Anotaciones](../README.md)