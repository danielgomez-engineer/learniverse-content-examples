# Guía Completa del Frontend Angular (Standalone) para Conectar con Backend Java EE

Esta guía documenta el proceso completo para crear el frontend Angular standalone (Angular 17+), conectando con el backend Java EE / WildFly. Incluye configuración de servicios, componentes, estilos y pruebas. El frontend expone una UI para CRUD de "Personas", compatible con las APIs REST del backend.

**Requisitos previos:**
- Backend corriendo en `http://localhost:8080/personas-backend-java/webservice/personas`.
- Node.js 18+ y Angular CLI instalados.
- Proyecto Angular creado con `ng new personas-frontend-angular --standalone`.
- `npm install` ejecutado.

**Objetivo:** UI en `http://localhost:4200` para listar, agregar y eliminar personas, conectando con el backend.

---

## Paso 1: Crear el Modelo Persona

Crea `src/app/persona.model.ts`:

```typescript
// Modelo de datos para Persona, igual al backend
export class Persona {
  constructor(
    public idPersona: number,  // ID único
    public nombre: string      // Nombre de la persona
  ) {}
}
```

**Explicación:** Clase simple para representar una persona, con propiedades públicas para asignación automática.

---

## Paso 2: Crear el Servicio PersonaService

Crea `src/app/persona.service.ts`:

```typescript
// Importaciones
import { Injectable } from '@angular/core';  // Decorador para servicios
import { HttpClient } from '@angular/common/http';  // Para llamadas HTTP
import { Observable } from 'rxjs';  // Para respuestas asíncronas
import { Persona } from './persona.model';  // Modelo Persona

// Servicio standalone
@Injectable({
  providedIn: 'root'  // Disponible globalmente
})
export class PersonaService {

  // URL del backend
  private urlBase = 'http://localhost:8080/personas-backend-java/webservice/personas';

  // Array local de personas
  personas: Persona[] = [];

  // Constructor inyecta HttpClient
  constructor(private httpClient: HttpClient) {}

  // Inicializa el array local
  setPersonas(personas: Persona[]): void {
    this.personas = personas;
  }

  // Carga personas desde backend
  cargarPersonas(): Observable<Persona[]> {
    return this.httpClient.get<Persona[]>(this.urlBase);
  }

  // Agrega persona al backend y local
  agregarPersona(persona: Persona): void {
    this.httpClient.post<Persona>(this.urlBase, persona).subscribe({
      next: (nuevaPersona) => {
        this.personas.push(nuevaPersona);  // Agrega localmente
      },
      error: (err) => console.error('Error agregando:', err)
    });
  }

  // Busca persona por ID
  encontrarPersona(id: number): Persona | undefined {
    return this.personas.find(p => p.idPersona === id);
  }

  // Modifica en backend
  modificarPersona(id: number, persona: Persona): void {
    this.httpClient.put(`${this.urlBase}/${id}`, persona).subscribe({
      next: () => console.log('Modificada'),
      error: (err) => console.error('Error modificando:', err)
    });
  }

  // Elimina del backend y local
  eliminarPersona(id: number): void {
    this.httpClient.delete(`${this.urlBase}/${id}`).subscribe({
      next: () => {
        this.personas = this.personas.filter(p => p.idPersona !== id);  // Remueve local
      },
      error: (err) => console.error('Error eliminando:', err)
    });
  }
}
```

**Explicación:** Servicio que maneja estado local y llamadas HTTP al backend. Usa `providedIn: 'root'` para inyección global.

---

## Paso 3: Actualizar AppComponent para Mostrar Lista

Reemplaza `src/app/app.component.ts`:

```typescript
// Importaciones
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';  // Para ngModel
import { PersonaService } from './persona.service';
import { Persona } from './persona.model';

// Componente standalone
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  title = 'personas-frontend-angular';
  personas: Persona[] = [];  // Lista para mostrar
  nuevoNombre: string = '';  // Para el formulario

  constructor(private personaService: PersonaService) {}

  ngOnInit() {
    this.cargarPersonas();  // Carga al iniciar
  }

  // Método para cargar personas
  cargarPersonas() {
    this.personaService.cargarPersonas().subscribe({
      next: (data) => {
        this.personas = data;
        this.personaService.setPersonas(data);
      },
      error: (err) => console.error('Error cargando:', err)
    });
  }

  // Método para agregar
  agregar() {
    if (this.nuevoNombre.trim()) {
      const nuevaPersona = new Persona(0, this.nuevoNombre);  // ID 0, backend lo asigna
      this.personaService.agregarPersona(nuevaPersona);
      this.nuevoNombre = '';
      this.cargarPersonas();  // Recarga lista
    }
  }

  // Método para eliminar
  eliminar(id: number) {
    this.personaService.eliminarPersona(id);
    this.cargarPersonas();  // Recarga lista
  }
}
```

Reemplaza `src/app/app.component.html`:

```html
<h1>{{ title }}</h1>

<!-- Lista de personas -->
<div class="lista-personas">
  <h2>Lista de Personas</h2>
  <ul *ngIf="personas.length > 0; else noPersonas">
    <li *ngFor="let persona of personas">
      {{ persona.nombre }} (ID: {{ persona.idPersona }})
      <button (click)="eliminar(persona.idPersona)">Eliminar</button>
    </li>
  </ul>
  <ng-template #noPersonas>
    <p>No hay personas.</p>
  </ng-template>
</div>

<!-- Formulario para agregar -->
<div class="form-persona">
  <h2>Agregar Persona</h2>
  <input [(ngModel)]="nuevoNombre" placeholder="Nombre" />
  <button (click)="agregar()">Agregar</button>
</div>
```

**Explicación:** Componente standalone que inyecta el servicio, carga datos y maneja UI. Template usa `*ngFor` y `[(ngModel)]` para binding.

---

## Paso 4: Estilos Básicos

Agrega a `src/styles.css`:

```css
/* Fondo global */
html {
  background-color: #f0f0f0;
  font-family: Arial, sans-serif;
}

/* Lista de personas */
.lista-personas {
  background-color: lightblue;
  padding: 20px;
  border: 1px solid blue;
  margin: 20px;
}

/* Formulario */
.form-persona {
  background-color: lightgreen;
  padding: 20px;
  border: 1px solid green;
  margin: 20px;
}
```

**Explicación:** Estilos globales simples para identificar secciones (azul para lista, verde para formulario).

---

## Paso 5: Probar Todo

1. Ejecuta `ng serve -o`.
2. Ve a `http://localhost:4200`.
3. Agrega personas con el formulario, elimina con botones.
4. Verifica en Postman que se actualice el backend.

**Errores comunes:**
- **CORS:** Configura en WildFly un filtro o subsystem para permitir `http://localhost:4200`.
- **404:** Backend no corriendo o URL incorrecta.
- **Errores de compilación:** Asegura que `main.ts` tenga `provideHttpClient()`.

---

## Conclusión

Con estos pasos, tienes un frontend completo conectando con el backend. Compatible con las APIs REST. Para producción, agrega validaciones y manejo de errores avanzado.

**Referencias:** Basado en Angular 17 standalone, HttpClient, y guías de conexión full-stack.

Si necesitas actualizar algo, consulta esta guía.

---

[⬅️ Volver al README del proyecto](../README.md)