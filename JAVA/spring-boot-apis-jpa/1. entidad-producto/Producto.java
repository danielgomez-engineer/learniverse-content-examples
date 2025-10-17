package com.learniverse.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entidad 'Producto': Representa la tabla 'productos' en la base de datos.
 * Utiliza anotaciones de JPA para el mapeo y Lombok para reducir código repetitivo.
 */
@Entity // Esta clase es una entidad
@Data // Genera automaticamente getters y setters 
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con argumentos
@Table(name = "productos") // Cambia el nombre de la tabla "producto" a "productos"
public class Producto {

    /**
     * Clave Primaria (Primary Key).
     * @Id: Marca el campo como identificador único.
     * @GeneratedValue: Define la estrategia de autogeneración (IDENTITY es común para MySQL/PostgreSQL).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Atributo 'nombre'.
     * @Column: Personaliza la columna en la DB.
     * name: El nombre real de la columna en la tabla.
     * nullable: Evita que el campo se guarde vacío (valor nulo).
     * unique: Asegura que no haya dos productos con el mismo nombre.
     */
    @Column(name = "nombre_producto", length = 100, nullable = false, unique = true)
    private String nombre;

    private Double precio; // Mapeo por defecto a la columna 'precio'

    // Getters, setters, constructores y toString generados por @Data, @NoArgsConstructor y @AllArgsConstructor
}