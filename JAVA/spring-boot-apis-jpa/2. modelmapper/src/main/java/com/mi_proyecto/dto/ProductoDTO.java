package com.mi_proyecto.dto;

import lombok.Data;

/**
 * DTO para transferir información del producto en la API.
 */
@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double precio;
}