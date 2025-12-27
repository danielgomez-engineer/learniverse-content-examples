# 🏷️ Anotaciones en Java | Guía Completa

Esta guía cubre todas las anotaciones más utilizadas en desarrollo Java moderno, organizadas por dependencia y categoría. Aprende cuándo, dónde y por qué usar cada una.

---

## 📚 Categorías de Anotaciones

| Categoría | Dependencia | Descripción | Enlace |
| :--- | :--- | :--- | :--- |
| **Lombok** | `org.projectlombok:lombok` | Reduce código repetitivo generando getters, setters, constructores, etc. | [Ver Anotaciones](anotaciones-lombok/README.md) |
| **Validation** | `javax.validation:validation-api` | Valida datos de entrada en DTOs y entidades. | [Ver Anotaciones](anotaciones-validation/README.md) |
| **JPA** | `jakarta.persistence` | Mapea clases Java a tablas de base de datos. | [Ver Anotaciones](anotaciones-jpa/README.md) |
| **Spring Core** | `spring-context` | Gestiona componentes, configuración e inyección de dependencias. | [Ver Anotaciones](anotaciones-spring/README.md) |
| **Spring Web** | `spring-web` | Controla endpoints REST y mapeo de solicitudes HTTP. | [Ver Anotaciones](anotaciones-spring-web/README.md) |

---

## 🎯 Tabla Rápida de Referencia

| Anotación | Dependencia | Dónde Usar | Función |
| :--- | :--- | :--- | :--- |
| **@Data** | Lombok | Clases | Genera getters, setters, toString, equals, hashCode |
| **@Builder** | Lombok | Clases | Patrón Builder para construcción de objetos |
| **@NoArgsConstructor** | Lombok | Clases | Constructor sin parámetros |
| **@AllArgsConstructor** | Lombok | Clases | Constructor con todos los parámetros |
| **@RequiredArgsConstructor** | Lombok | Clases | Constructor con parámetros final |
| **@Getter** | Lombok | Clases | Genera todos los getters |
| **@Setter** | Lombok | Clases | Genera todos los setters |
| **@Slf4j** | Lombok | Clases | Inyecta logger SLF4J automáticamente |
| **@NotNull** | Validation | Atributos | Valida que el campo no sea null |
| **@NotBlank** | Validation | Atributos | Valida que String no esté vacío (sin espacios) |
| **@NotEmpty** | Validation | Atributos | Valida que colecciones/strings no estén vacíos |
| **@Email** | Validation | Atributos | Valida formato de email |
| **@Min** | Validation | Atributos | Valida valor mínimo numérico |
| **@Max** | Validation | Atributos | Valida valor máximo numérico |
| **@Size** | Validation | Atributos | Valida tamaño de colecciones/strings |
| **@Pattern** | Validation | Atributos | Valida con expresión regular |
| **@Valid** | Validation | Parámetros | Valida objetos anidados |
| **@Entity** | JPA | Clases | Define una clase como entidad JPA |
| **@Table** | JPA | Clases | Personaliza nombre de tabla |
| **@Id** | JPA | Atributos | Define clave primaria |
| **@GeneratedValue** | JPA | Atributos | Auto-genera valores (secuencias, identity) |
| **@Column** | JPA | Atributos | Personaliza columna (nullable, unique, length) |
| **@ManyToOne** | JPA | Atributos | Relación muchos a uno |
| **@OneToMany** | JPA | Atributos | Relación uno a muchos |
| **@ManyToMany** | JPA | Atributos | Relación muchos a muchos |
| **@OneToOne** | JPA | Atributos | Relación uno a uno |
| **@JoinColumn** | JPA | Atributos | Personaliza columna de relación |
| **@JoinTable** | JPA | Atributos | Personaliza tabla intermedia en N:N |
| **@CreationTimestamp** | Hibernate | Atributos | Registra fecha de creación automáticamente |
| **@UpdateTimestamp** | Hibernate | Atributos | Registra fecha de última actualización |
| **@Transient** | JPA | Atributos | Excluye campo del mapeo a BD |
| **@Enumerated** | JPA | Atributos | Mapea enums a BD |
| **@Lob** | JPA | Atributos | Para datos grandes (TEXT, BLOB) |
| **@Service** | Spring | Clases | Marca como servicio (lógica de negocio) |
| **@Repository** | Spring | Clases | Marca como repositorio (acceso a datos) |
| **@Controller** | Spring | Clases | Marca como controlador MVC |
| **@RestController** | Spring | Clases | Controlador que retorna JSON/XML |
| **@Component** | Spring | Clases | Marca como componente genérico |
| **@Configuration** | Spring | Clases | Define clase de configuración |
| **@Bean** | Spring | Métodos | Define bean administrado por Spring |
| **@Autowired** | Spring | Campos/Métodos | Inyecta dependencia automáticamente |
| **@Qualifier** | Spring | Campos | Especifica qué bean inyectar |
| **@Primary** | Spring | Métodos | Marca bean como prioritario |
| **@Value** | Spring | Campos | Inyecta valor de properties |
| **@ConfigurationProperties** | Spring | Clases | Mapea propiedades a clase |
| **@RequestMapping** | Spring Web | Métodos/Clases | Mapea rutas HTTP |
| **@GetMapping** | Spring Web | Métodos | Mapea solicitudes GET |
| **@PostMapping** | Spring Web | Métodos | Mapea solicitudes POST |
| **@PutMapping** | Spring Web | Métodos | Mapea solicitudes PUT |
| **@DeleteMapping** | Spring Web | Métodos | Mapea solicitudes DELETE |
| **@PatchMapping** | Spring Web | Métodos | Mapea solicitudes PATCH |
| **@RequestBody** | Spring Web | Parámetros | Convierte JSON a objeto Java |
| **@ResponseBody** | Spring Web | Métodos | Convierte objeto Java a JSON |
| **@RequestParam** | Spring Web | Parámetros | Obtiene parámetros de query string |
| **@PathVariable** | Spring Web | Parámetros | Obtiene variables de ruta |
| **@CrossOrigin** | Spring Web | Métodos/Clases | Habilita CORS |
| **@ExceptionHandler** | Spring Web | Métodos | Maneja excepciones específicas |
| **@ResponseStatus** | Spring Web | Métodos/Excepciones | Define código HTTP de respuesta |

---

## 📖 Cómo Usar Esta Guía

1. **Navega por categoría** usando los enlaces arriba.
2. **Cada sección detalla:**
   - Anotaciones de la dependencia
   - Cuándo usar cada una
   - Ejemplos de código
   - Casos reales de uso

---
[Volver al Índice de JAVA](../README.md)