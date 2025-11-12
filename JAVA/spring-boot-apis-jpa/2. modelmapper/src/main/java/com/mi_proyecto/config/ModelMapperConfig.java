
package com.mi_proyecto.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Configuración avanzada: Mapeo personalizado si nombres difieren
        // Ejemplo: si en entidad es 'nombreProducto' y en DTO 'nombre'
        // mapper.addMappings(new PropertyMap<Producto, ProductoDTO>() {
        //     @Override
        //     protected void configure() {
        //         map().setNombre(source.getNombreProducto());
        //     }
        // });

        return mapper;
    }
}
