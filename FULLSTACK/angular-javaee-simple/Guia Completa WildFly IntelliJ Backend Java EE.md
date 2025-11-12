🧩 Guía de Instalación de WildFly 30

Esta guía detalla paso a paso cómo descargar e instalar WildFly 30 como servidor de aplicaciones Java EE / Jakarta EE.

---

🧩 1. ¿Qué es WildFly?

WildFly (antes JBoss AS) es un servidor de aplicaciones Java EE / Jakarta EE gratuito y open-source. Ejecuta aplicaciones .war/.ear, manejando EJBs, JPA, JAX-RS, transacciones, seguridad, etc.

- **Ventajas:** Soporte completo para Jakarta EE 10, administración vía web/CLI, integración con IDEs como IntelliJ.
- **Versión:** 30.0.0.Final.
- **Uso:** Despliega backends Java EE con conexiones a BD como MySQL.

---

🧱 2. Descargar e Instalar WildFly 30

### Paso 2.1: Descargar WildFly
- Ve a https://www.wildfly.org/downloads/.
- Descarga "WildFly 30.0.0.Final" (ZIP para Windows).
- Extrae en `C:\wildfly-30.0.0.Final` (carpeta sin espacios).

### Paso 2.2: Instalar JDK 17+
- WildFly requiere JDK 11+, usa 17 para compatibilidad.
- Descarga JDK 17 de https://adoptium.net/.
- Instala y configura `JAVA_HOME`: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x-hotspot`.
- Verifica: CMD > `java -version` → JDK 17.

### Paso 2.3: Iniciar WildFly
- CMD como admin > `cd C:\wildfly-30.0.0.Final\bin`.
- Ejecuta `standalone.bat`.
- Espera "WildFly Full 30.0.0.Final started".
- Consola web: `http://localhost:9990` (usuario admin/admin).
- Detén con Ctrl+C.

### Paso 2.4: Cambiar Puerto (Opcional)
- Edita `C:\wildfly-30.0.0.Final\standalone\configuration\standalone.xml`.
- Busca `<socket-binding-group>`.
- Cambia `<socket-binding name="http" port="${jboss.http.port:9090}"/>`.
- Reinicia.

---

⚙️ 3. Añadir WildFly a IntelliJ como Nuevo Servidor

### Paso 3.1: Instalar IntelliJ
- Descarga Community Edition de https://www.jetbrains.com/idea/download/.
- Instala y abre.

### Paso 3.2: Configurar WildFly en IntelliJ
- File > Settings > Build, Execution, Deployment > Application Servers.
- Add > JBoss Server > Local.
- Name: WildFly 30.
- Home: `C:\wildfly-30.0.0.Final`.
- JRE: JDK 17.
- OK.
- Ahora WildFly aparece como servidor disponible en IntelliJ.

---

📦 4. Crear el Proyecto en IntelliJ y Configurar el Artefacto

### Paso 4.1: Nuevo Proyecto Maven
- File > New > Project > Maven.
- Name: `personas-backend-java`.
- GroupId: `com.prodigygomez`.
- ArtifactId: `personas-backend-java`.
- JDK: 17.
- Archetype: `maven-archetype-webapp`.
- Finish.

### Paso 4.2: Configurar pom.xml
- Agrega dependencias Jakarta EE y MySQL:
  ```xml
  <dependencies>
    <dependency>
      <groupId>jakarta.platform</groupId>
      <artifactId>jakarta.jakartaee-api</artifactId>
      <version>10.0.0</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>com.mysql</groupId>
      <artifactId>mysql-connector-j</artifactId>
      <version>8.4.0</version>
      <scope>runtime</scope>
    </dependency>
  </dependencies>
  ```
- Build: `<finalName>personas-backend-java</finalName>`.

### Paso 4.3: Configurar Artefacto
- File > Project Structure > Artifacts.
- Add > Web Application: Archive > From Modules.
- Selecciona `personas-backend-java`.
- Output: `personas-backend-java:war exploded`.
- Build on make: Sí.
- OK.
- Esto crea el artefacto .war que se despliega en WildFly.

---

🚀 5. Subir el WAR (Desplegar en WildFly)

### Paso 5.1: Configurar Run en IntelliJ
- Run > Edit Configurations > Add > JBoss Server > Local.
- Server: WildFly 30.
- VM options: `-Djboss.http.port=9090`.
- Deployment: Add > Artifact > personas-backend-java:war exploded.
- Run.
- IntelliJ inicia WildFly y despliega el .war automáticamente.

### Paso 5.2: Verificar Despliegue
- Logs: "Deployed personas-backend-java.war".
- Consola WildFly: Runtime > Deployments → OK.

**Nota:** El WAR no se sube manualmente. IntelliJ lo despliega automáticamente al ejecutar la configuración de Run.

---

🧠 6. Agregar el Conector MySQL a WildFly via Consola Web (localhost:9990)

### Paso 6.1: Descargar MySQL Connector/J
- Ve a https://dev.mysql.com/downloads/connector/j/.
- Descarga `mysql-connector-j-8.4.0.jar`.
- Guárdalo en `C:\temp`.

### Paso 6.2: Crear el Módulo en WildFly
- Crea carpetas: `C:\wildfly-30.0.0.Final\modules\com\mysql\main\`.
- Copia `mysql-connector-j-8.4.0.jar` ahí.
- Crea `module.xml`:
  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <module xmlns="urn:jboss:module:1.9" name="com.mysql">
    <resources><resource-root path="mysql-connector-j-8.4.0.jar"/></resources>
    <dependencies><module name="jakarta.api"/></dependencies>
  </module>
  ```

### Paso 6.3: Registrar el Driver via Consola Web
- Inicia WildFly (`standalone.bat`).
- Ve a `http://localhost:9990` > Login admin/admin.
- Configuration > Subsystems > Datasources & Drivers > JDBC Drivers.
- Add JDBC Driver.
- Name: `mysql`.
- Driver Class Name: `com.mysql.cj.jdbc.Driver`.
- Driver Module Name: `com.mysql`.
- Save.
- Verifica que aparezca "mysql" en la lista de drivers.

---

[⬅️ Volver al README del proyecto](README.md)