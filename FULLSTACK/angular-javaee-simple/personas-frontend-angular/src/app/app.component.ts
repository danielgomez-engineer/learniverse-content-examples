import { Component, OnInit } from '@angular/core';
import { PersonaService } from './persona.service';
import { Persona } from './Persona.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'personas-frontend-angular';
  nuevoNombre: string = ""; // para formulario

  personas: Persona[] = []; //array para la lista de personas

  // constructor que inyecta el servicio
  constructor(private personaService: PersonaService) {}

  // al iniciar carga personas desde elbackend
  ngOnInit() {
    this.cargarPersonas();
  }

  cargarPersonas() {
    this.personaService.cargarPersonas().subscribe({
      next: (data) => {
        this.personas = data;
        this.personaService.setPersonas(data);
      },
      error: (err) => console.error('Error cargando personas ', err)
    });
  }

agregar() {
  if (this.nuevoNombre.trim()) {
    const nuevaPersona = { nombre: this.nuevoNombre }; // solo el nombre
    this.personaService.agregarPersona(nuevaPersona);
    this.nuevoNombre = '';
    this.cargarPersonas();
  }
}


  eliminar(id: number ) {
    this.personaService.eliminarPersona(id);
    this.cargarPersonas();
  }


  
}
