import { Injectable } from '@angular/core';
import { Persona } from './Persona.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})    // MANEJA estado local y llamadas HTTP al backend
export class PersonaService {

  //url del backend
  private urlBase = 'http://localhost:9090/personas-backend-java/webservice/personas';

  // array local de personas
  personas: Persona [] = [];

  //constructor que inyecta HttpClient
  constructor(private httpClient: HttpClient) { }

  //inicializa el array local
  setPersonas(personas: Persona[]): void {
    this.personas = personas;
  }

  // cargar personas
  cargarPersonas(): Observable<Persona[]> {
    return this.httpClient.get<Persona[]>(this.urlBase);
  }

  //agregar persona al backend y al local
agregarPersona(persona: any): void {
  this.httpClient.post<Persona>(this.urlBase, persona).subscribe({
    next: (nuevaPersona) => this.personas.push(nuevaPersona),
    error: (err) => console.error('Error agregando: ', err)
  });
}


  //buscar persona por id
  encontrarPersona(id: number): Persona | undefined {
    return this.personas.find(p => p.idPersona === id);
  }

  // modifica el backend
  modificarPersona(id: number, persona: Persona): void {
    this.httpClient.put(`${this.urlBase}/${id}`, persona).subscribe ({
      next: () => console.log('Modificada'),
      error: (err) => console.error('Error modificando, ' + err)
    });
  }

  // eliminar persona del backend y local
  eliminarPersona(id:number): void {
    this.httpClient.delete(`${this.urlBase}/${id}`).subscribe ({
      next: () => {
        this.personas = this.personas.filter(p => p.idPersona !== id ); //remueve local
      },
      error: (err) => console.error('Error eliminando ', err)
    });
  }



}
