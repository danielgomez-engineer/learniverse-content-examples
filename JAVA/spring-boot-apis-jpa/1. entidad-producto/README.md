# 🌟 Módulo 01: Creando la Entidad Producto en Spring Boot

Este proyecto contiene la implementación de la clase `Producto.java`, esencial para la persistencia de datos con **Spring Data JPA**. El objetivo es mostrar cómo mapear una clase de Java a una tabla de base de datos de manera profesional.

## 🔑 Archivo Clave

El código principal y comentado se encuentra aquí: [`Producto.java`](Producto.java).

### Anotaciones Clave Explicadas

* `@Entity` y `@Table(name="...")`: Para definir la clase como una tabla y personalizar su nombre.
* `@Id` y `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Para establecer la llave primaria y su autogeneración.
* `@Column(nullable=false, unique=true)`: Para aplicar restricciones de columna (no nulo, único) directamente desde Java.

## 🎬 Video Tutorial Completo

Si deseas una explicación visual y dinámica de por qué y cómo usar estas anotaciones, mira el tutorial completo en nuestro canal:

🔗 **Qué es una Entidad en Spring Boot y JPA | Aprende a mapear tu base de datos paso a paso**

[Video](https://www.youtube.com/watch?v=ajU38ZUBro0)

---
[Volver al Índice del Módulo](../README.md)