# 🌟 Módulo 02: Introducción a ModelMapper en Spring Boot

Este módulo enseña cómo usar ModelMapper para mapear automáticamente entre entidades JPA y DTOs en Spring Boot, reduciendo código repetitivo y mejorando la arquitectura.

## 1. Requisitos y Preparación 🚀💻

Asegúrate de tener un proyecto Spring Boot configurado con dependencias para APIs REST, DTOs y entidades.

**ModelMapper** es una librería liviana que convierte objetos automáticamente (ej. entidad a DTO).

### 📦 Dependencias esenciales

Ver en [pom.xml](pom.xml) para las dependencias completas, incluyendo Spring Boot Web, Data JPA, Lombok y ModelMapper.

### 🔑 Archivos Clave

- [Application](src/main/java/com/mi_proyecto/Application.java) : Clase principal de Spring Boot.
- [Producto](src/main/java/com/mi_proyecto/model/Producto.java): Entidad JPA.
- [ProductoDTO](src/main/java/com/mi_proyecto/dto/ProductoDTO.java): DTO para transferir datos.
- [ProductoRepository](src/main/java/com/mi_proyecto/repository/ProductoRepository.java): Repositorio para operaciones de base de datos.
- [ProductoService](src/main/java/com/mi_proyecto/service/ProductoService.java): Servicio con lógica de negocio y mapeos.
- [ModelMapperConfig](src/main/java/com/mi_proyecto/config/ModelMapperConfig.java): Configuración de ModelMapper.

## 2. Definición y Propósito de ModelMapper 🧩

ModelMapper automatiza conversiones entre objetos con estructuras similares, como entidades JPA y DTOs.

- **Propósito:** Separar capas (persistencia vs. presentación), reducir código boilerplate y facilitar mantenimiento.
- **Ventajas:** Ahorro de tiempo, flexibilidad, integración fácil y menor acoplamiento.

Ejemplo básico: Convierte una entidad Producto a ProductoDTO y viceversa con una sola línea.

## 3. Configuración de ModelMapper en Spring Boot ⚙️

Registra ModelMapper como un bean en Spring para inyección global.

Crea una clase de configuración en `src/main/java/com/mi_proyecto/config/ModelMapperConfig.java` con un método anotado con `@Bean` que devuelve una instancia de ModelMapper.

Inyéctalo en servicios usando `@Autowired`.

## 4. Creación de DTOs (Data Transfer Objects) 📦

Los DTOs transportan datos entre API y capas internas, sin exponer entidades directamente.

- Ubicación: `src/main/java/com/mi_proyecto/dto/`
- Usa Lombok para simplicidad.

Ver en [ProductoDTO](src/main/java/com/mi_proyecto/dto/ProductoDTO.java).

## 5. Mapeo de Entidades y DTOs 🔄

Usa ModelMapper para conversiones directas.

- Entidad → DTO: Para respuestas API.
- DTO → Entidad: Para guardar datos.

Ver en [ProductoService](src/main/java/com/mi_proyecto/service/ProductoService.java) para métodos de conversión y operaciones CRUD completas.

## 6. Uso de ModelMapper en métodos de servicio (casos reales) 🧩

Combina mapeo con lógica adicional.

Ver en [ProductoService](src/main/java/com/mi_proyecto/service/ProductoService.java) para ejemplos completos de CRUD: crear, listar, obtener por ID, actualizar y eliminar productos, con manejo de errores y lógica adicional.

## 7. Mapeos Personalizados y Configuraciones Avanzadas 🔧

Para campos con nombres diferentes o estructuras complejas, configura mapeos personalizados usando `PropertyMap` en `ModelMapperConfig.java`.

- **Mapeos personalizados:** Si los nombres de campos no coinciden (ej. `nombreProducto` en entidad vs. `nombre` en DTO), usa `PropertyMap` para mapear explícitamente.
- **Validaciones:** Agrega `@Valid` en DTOs para validaciones automáticas (requiere dependencias de validación como Hibernate Validator).
- **Errores:** Maneja excepciones en mapeos (ej. campos nulos) con try-catch en servicios como `ProductoService.java`.

Ver configuraciones avanzadas en [ModelMapperConfig](src/main/java/com/mi_proyecto/config/ModelMapperConfig.java) y ejemplos de uso en `ProductoService.java`.

¡Experimenta y adapta a tus necesidades!
---
[Volver al Índice del Módulo](../README.md)