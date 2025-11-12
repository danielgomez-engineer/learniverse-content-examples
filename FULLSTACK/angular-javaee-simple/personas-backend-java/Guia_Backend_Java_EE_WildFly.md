# Guía Completa: Creación del Backend Java EE con WildFly 30

Esta guía documenta el proceso completo para crear un backend Java EE / Jakarta EE 10 usando WildFly 30 como servidor de aplicaciones. Incluye configuración de base de datos MySQL, JPA para persistencia, EJB para servicios, y JAX-RS para APIs REST. El backend expone endpoints para CRUD de "Personas", compatible con el frontend Angular.

**Requisitos previos:**
- Java 17+ instalado.
- MySQL 8+ instalado y corriendo.
- WildFly 30 descargado e instalado.
- IDE: NetBeans o IntelliJ IDEA.
- Maven para gestión de dependencias.

**Objetivo:** APIs REST en `http://localhost:8080/personas-backend-java/webservice/personas` para listar, agregar, modificar y eliminar personas.

---

## Paso 1: Instalar y Configurar WildFly 30

1. Descarga WildFly 30 desde [wildfly.org](https://www.wildfly.org/).
2. Extrae el ZIP en una carpeta, ej. `C:\wildfly-30.0.0.Final`.
3. Configura el datasource MySQL:
   - Edita `standalone/configuration/standalone.xml`.
   - Agrega en la sección `<datasources>`:
     ```xml
     <datasource jndi-name="java:jboss/datasources/PersonasDS" pool-name="PersonasDS">
         <connection-url>jdbc:mysql://localhost:3306/personas?serverTimezone=America/Bogota</connection-url>
         <driver>mysql</driver>
         <security>
             <user-name>root</user-name>
             <password>admin</password>
         </security>
     </datasource>
     <drivers>
         <driver name="mysql" module="com.mysql">
             <xa-datasource-class>com.mysql.cj.jdbc.MysqlXADataSource</xa-datasource-class>
         </driver>
     </drivers>
     ```
   - Crea el módulo MySQL: En `modules/system/layers/base/com/mysql/main/`, agrega `module.xml` y el JAR de MySQL Connector.
4. Inicia WildFly: Ejecuta `bin/standalone.bat` (Windows) o `bin/standalone.sh` (Linux/Mac).
5. Accede al admin console: `http://localhost:9990` (usuario/admin por defecto).

**Nota:** Si usas CLI, ejecuta comandos como `data-source add --name=PersonasDS --connection-url=jdbc:mysql://localhost:3306/personas --driver-name=mysql --user-name=root --password=admin`. El datasource debe configurarse en WildFly antes del despliegue.

---

## Paso 2: Crear el Proyecto Maven

1. Abre NetBeans o IntelliJ IDEA.
2. Crea un nuevo proyecto: `File > New > Project > Maven > Web Application`.
3. Configura:
   - **Group ID:** `com.prodigygomez`
   - **Artifact ID:** `personas-backend-java`
   - **Version:** `1.0.0`
   - **Packaging:** `war`
   - **Java Version:** 17
4. Selecciona WildFly 30 como servidor (configúralo si no aparece).
5. Crea la estructura de carpetas manualmente si no se genera:
   - `src/main/java/domain/`
   - `src/main/java/datos/`
   - `src/main/java/servicio/`
   - `src/main/java/web/`
   - `src/main/resources/META-INF/`

---

## Paso 3: Configurar pom.xml

Edita `pom.xml` con el siguiente contenido:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.prodigygomez</groupId>
  <artifactId>personas-backend-java</artifactId>
  <version>1.0.0</version>
  <packaging>war</packaging>

  <properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>
    <!-- Jakarta EE 10 API -->
    <dependency>
      <groupId>jakarta.platform</groupId>
      <artifactId>jakarta.jakartaee-api</artifactId>
      <version>10.0.0</version>
      <scope>provided</scope>
    </dependency>

    <!-- MySQL Connector -->
    <dependency>
      <groupId>com.mysql</groupId>
      <artifactId>mysql-connector-j</artifactId>
      <version>9.2.0</version>
      <scope>runtime</scope>
    </dependency>
  </dependencies>
</project>
```

**Explicación:** Incluye APIs de Jakarta EE y el driver MySQL. `scope=provided` porque WildFly las provee.

---

## Paso 4: Configurar persistence.xml

Crea/edita `src/main/resources/META-INF/persistence.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence" version="3.1">
  <persistence-unit name="personasPU" transaction-type="JTA">
    <jta-data-source>java:jboss/datasources/PersonasDS</jta-data-source>
    <class>domain.Persona</class>
    <properties>
      <property name="jakarta.persistence.schema-generation.database.action" value="none"/>
      <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/personas?serverTimezone=America/Bogota"/>
      <property name="jakarta.persistence.jdbc.user" value="root"/>
      <property name="jakarta.persistence.jdbc.password" value="admin"/>
    </properties>
  </persistence-unit>
</persistence>
```

**Explicación:** Unidad de persistencia para JPA. `drop-and-create` crea la tabla automáticamente. Ajusta URL/credenciales si es necesario.

---

## Paso 5: Crear la Clase Entidad Persona

Crea `src/main/java/domain/Persona.java`:

```java
package domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Integer idPersona;

    private String nombre;

    // Getters y Setters
    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
```

**Explicación:** Entidad JPA que mapea a la tabla `persona`. `@Id` para clave primaria auto-generada.

---

## Paso 6: Crear la Clase DAO (PersonaDao)

Crea `src/main/java/datos/PersonaDao.java`:

```java
package datos;

import domain.Persona;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class PersonaDao {

    @PersistenceContext(unitName = "personasPU")
    private EntityManager em;

    public List<Persona> listarPersonas() {
        return em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
    }

    public void insertarPersona(Persona persona) {
        em.persist(persona);
    }

    public void modificarPersona(Persona persona) {
        em.merge(persona);
    }

    public void eliminarPersona(Persona persona) {
        em.remove(em.merge(persona));
    }
}
```

**Explicación:** DAO con EJB `@Stateless`. Usa `EntityManager` para operaciones CRUD.

---

## Paso 7: Crear la Clase de Servicio (PersonaService)

Crea `src/main/java/servicio/PersonaService.java`:

```java
package servicio;

import datos.PersonaDao;
import domain.Persona;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class PersonaService {

    @Inject
    private PersonaDao personaDao;

    public List<Persona> listarPersonas() {
        return personaDao.listarPersonas();
    }

    public void registrarPersona(Persona persona) {
        personaDao.insertarPersona(persona);
    }

    public void actualizarPersona(Persona persona) {
        personaDao.modificarPersona(persona);
    }

    public void eliminarPersona(Persona persona) {
        personaDao.eliminarPersona(persona);
    }
}
```

**Explicación:** Servicio EJB que inyecta DAO y delega operaciones.

---

## Paso 8: Crear la Clase REST (PersonaResource)

Crea `src/main/java/web/PersonaResource.java`:

```java
package web;

import domain.Persona;
import servicio.PersonaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/personas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonaResource {

    @Inject
    private PersonaService personaService;

    @GET
    public List<Persona> listarPersonas() {
        return personaService.listarPersonas();
    }

    @POST
    public Response agregarPersona(Persona persona) {
        personaService.registrarPersona(persona);
        return Response.status(Response.Status.CREATED).entity(persona).build();
    }

    @PUT
    @Path("{id}")
    public Response modificarPersona(@PathParam("id") Integer id, Persona persona) {
        persona.setIdPersona(id);
        personaService.actualizarPersona(persona);
        return Response.ok(persona).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarPersona(@PathParam("id") Integer id) {
        Persona persona = new Persona();
        persona.setIdPersona(id);
        personaService.eliminarPersona(persona);
        return Response.ok(persona).build();
    }
}
```

**Explicación:** Controlador REST con JAX-RS. Endpoints para CRUD.

---

## Paso 9: Configurar ApplicationConfig

Crea `src/main/java/web/ApplicationConfig.java`:

```java
package web;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/webservice")
public class ApplicationConfig extends Application {
}
```

**Explicación:** Define el path base `/webservice` para las APIs.

---

## Paso 10: Configurar CORS para Angular

Crea `src/main/java/web/CorsFilter.java`:

```java
package web;

import java.io.IOException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {

        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
```

**Explicación:** Filtro JAX-RS para habilitar CORS, permitiendo requests desde Angular en `localhost:4200`.

**Nota:** No es necesario `web.xml` en `src/main/webapp/WEB-INF/`, ya que `ApplicationConfig` y `@Provider` configuran automáticamente el contexto.

---

## Paso 11: Desplegar y Probar

1. Compila: `mvn clean compile`.
2. Despliega en WildFly via IDE o admin console.
3. Prueba con Postman:
   - **GET:** `http://localhost:8080/personas-backend-java/webservice/personas` → Lista personas.
   - **POST:** URL + body `{"nombre": "Juan"}` → Agrega persona.
   - **PUT:** URL + `/1` + body `{"nombre": "Juan Actualizado"}` → Modifica.
   - **DELETE:** URL + `/1` → Elimina.

**Errores comunes:**
- BD no conecta: Revisa datasource en WildFly.
- 404: Proyecto no desplegado.
- Logs en `wildfly-30.0.0.Final/standalone/log/server.log`.

---

## Conclusión

Con estos pasos, tienes un backend completo con APIs REST y CORS habilitado. Compatible con Angular. Para producción, ajusta credenciales y configura el datasource en WildFly.

**Referencias:** Basado en Jakarta EE 10, WildFly 30 docs, y guías de GlobalMentoring.

Si necesitas actualizar algo, consulta esta guía.

---

[⬅️ Volver al README del proyecto](../README.md)