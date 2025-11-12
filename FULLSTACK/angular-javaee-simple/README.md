# 🌐 Proyecto Fullstack: Angular + Java EE (Nivel Principiante)

Este proyecto demuestra la integración entre un **frontend en Angular (standalone)** y un **backend en Java EE (Jakarta EE 10)** desplegado en **WildFly**.  
Su objetivo es enseñar los fundamentos del desarrollo **fullstack** utilizando tecnologías modernas de frontend y backend.

---

## 🧩 Resumen del Proyecto

**Nombre:** `angular-javaee-simple`  
**Nivel:** Principiante  
**Propósito:** Aprender cómo conectar un frontend Angular con un backend Java EE para realizar operaciones CRUD básicas sobre una entidad `Persona`.

### 📘 Descripción General

| Elemento | Detalle |
| :--- | :--- |
| **Frontend** | Angular standalone con servicios HTTP (`HttpClient`) para consumir las APIs REST. |
| **Backend** | Java EE (Jakarta EE 10) con EJBs, JPA y JAX-RS, desplegado en WildFly. |
| **Base de Datos** | MySQL con una tabla `persona (id_persona, nombre)`. |
| **Comunicación** | Angular (http://localhost:4200) → Java EE API (http://localhost:8080/personas-backend-java/webservice/personas). |
| **Operaciones CRUD** | Crear, listar, modificar y eliminar personas. |
| **CORS** | Configurado en el backend para permitir solicitudes desde el frontend. |

---

## ⚙️ Arquitectura del Proyecto

El proyecto sigue una arquitectura **fullstack clásica** con separación clara entre capas. A continuación, la estructura detallada:

```
angular-javaee-simple/
├── personas-backend-java/                    # Backend en Java EE
│   ├── src/main/java/
│   │   ├── domain/                          # Capa de dominio (entidades JPA)
│   │   │   └── Persona.java                 # Entidad Persona (id_persona, nombre)
│   │   ├── datos/                           # Capa de datos (DAO)
│   │   │   └── PersonaDao.java              # Acceso a BD con EntityManager
│   │   ├── servicio/                        # Capa de servicio (lógica de negocio)
│   │   │   └── PersonaService.java          # Servicio EJB con operaciones CRUD
│   │   └── web/                             # Capa web (APIs REST)
│   │       ├── ApplicationConfig.java       # Configuración JAX-RS (/webservice)
│   │       ├── CorsFilter.java              # Filtro CORS para frontend
│   │       └── PersonaResource.java         # Endpoints REST (GET, POST, PUT, DELETE)
│   ├── src/main/resources/META-INF/
│   │   └── persistence.xml                   # Configuración JPA (datasource JNDI)
│   ├── pom.xml                              # Dependencias Maven (Jakarta EE, MySQL)
│   └── Guia_Backend_Java_EE_WildFly.md      # Guía de implementación backend
│
├── personas-frontend-angular/                # Frontend en Angular
│   ├── Guia_Frontend_Angular_Completa.md    # Guía completa para crear el proyecto
│   └── (archivos fuente: persona.model.ts, persona.service.ts, app.component.*)
│
├── Guia Completa WildFly IntelliJ Backend Java EE.md  # Guía de configuración WildFly
└── README.md                               # Este archivo (documentación general)
```

### 🧩 Descripción de Capas

- **Capa Web (Frontend):** Angular standalone consume APIs REST del backend. Maneja UI, formularios y estado local.
- **Capa de Servicio (Backend):** JAX-RS expone endpoints REST. Usa EJBs para lógica transaccional.
- **Capa de Datos (Backend):** JPA gestiona persistencia en MySQL. DAO abstrae consultas.
- **Configuración:** WildFly como servidor, con datasource JNDI y módulo MySQL.
---

---

## 🚀 Flujo de Ejecución

1. El usuario interactúa con la **interfaz Angular** (formulario para agregar o eliminar personas).  
2. El servicio Angular envía solicitudes HTTP (GET, POST, PUT, DELETE) al backend.  
3. El backend **procesa la petición** mediante EJBs, gestiona la persistencia con JPA y responde en formato JSON.  
4. Angular actualiza la vista automáticamente tras recibir la respuesta.

---

## 🧠 Conceptos Clave

- Conexión entre **Angular y Java EE** mediante APIs REST.
- Uso de **JPA** y **DataSource JNDI** para la persistencia de datos.
- Configuración de **CORS** para comunicación local.
- Despliegue de WAR en **WildFly**.
- Consumo de endpoints desde Angular mediante **HttpClient**.

---

## 📚 Guías Completas

Consulta las guías paso a paso para entender cada parte del proyecto:

| Guía | Descripción | Enlace |
| :--- | :--- | :--- |
| 🧩 **Guía Completa Backend (Java EE)** | Explica la estructura, clases y configuración de persistencia con JPA. | [Abrir Guía](personas-backend-java/Guia_Backend_Java_EE_WildFly.md) |
| 🎨 **Guía Completa Frontend (Angular)** | Describe cómo crear el proyecto Angular standalone y consumir la API REST. | [Abrir Guía](personas-frontend-angular/Guia_Frontend_Angular_Completa.md) |
| ⚙️ **Guía de Configuración de WildFly** | Muestra cómo configurar el servidor, datasource y desplegar el WAR. | [Abrir Guía](Guia%20Completa%20WildFly%20IntelliJ%20Backend%20Java%20EE.md) |

---

## 🧾 Estado del Proyecto

✅ **Backend:** Implementado y funcional  
🧭 **Frontend:** Documentado (guía completa disponible)  
🧩 **Nivel:** Principiante  
📦 **Tipo:** Proyecto educativo fullstack (sin autenticación)

---

[⬅️ Volver a la lista de proyectos](../README.md)
