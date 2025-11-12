package com.mi_proyecto.service;

import com.mi_proyecto.dto.ProductoDTO;
import com.mi_proyecto.model.Producto;
import com.mi_proyecto.repository.ProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoService {

    private final ModelMapper modelMapper;
    private final ProductoRepository productoRepository;

    public ProductoService(ModelMapper modelMapper, ProductoRepository productoRepository) {
        this.modelMapper = modelMapper;
        this.productoRepository = productoRepository;
    }

    // Crear producto
    public ProductoDTO createProducto(ProductoDTO request) {
        Producto producto = modelMapper.map(request, Producto.class);
        producto.setFechaCreacion(LocalDateTime.now()); // Lógica adicional
        Producto savedProducto = productoRepository.save(producto);
        return modelMapper.map(savedProducto, ProductoDTO.class);
    }

    // Obtener todos los productos
    public List<ProductoDTO> findAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        if (productos.isEmpty()) {
            throw new RuntimeException("No hay productos disponibles");
        }
        return productos.stream()
                .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .toList();
    }

    // Obtener producto por ID
    public ProductoDTO findByIdProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return modelMapper.map(producto, ProductoDTO.class);
    }

    // Actualizar producto
    public ProductoDTO updateProducto(ProductoDTO request, Long id) {
        Producto existingProducto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar campos manualmente o usar mapeo
        existingProducto.setNombre(request.getNombre());
        existingProducto.setPrecio(request.getPrecio());
        existingProducto.setFechaActualizacion(LocalDateTime.now()); // Lógica adicional

        Producto updatedProducto = productoRepository.save(existingProducto);
        return modelMapper.map(updatedProducto, ProductoDTO.class);
    }

    // Eliminar producto
    public void deleteProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    // Mapeo básico: Entidad a DTO (para compatibilidad)
    public ProductoDTO convertirAProductoDTO(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }

    // Mapeo básico: DTO a Entidad (para compatibilidad)
    public Producto convertirAProducto(ProductoDTO dto) {
        return modelMapper.map(dto, Producto.class);
    }

    // Configuración avanzada: Uso con validaciones y errores
    public ProductoDTO procesarProducto(ProductoDTO dto) {
        try {
            Producto producto = modelMapper.map(dto, Producto.class);
            // Lógica adicional
            return modelMapper.map(producto, ProductoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error en mapeo: " + e.getMessage());
        }
    }
}