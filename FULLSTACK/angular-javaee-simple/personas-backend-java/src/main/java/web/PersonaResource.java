package web;

import domain.Persona;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicio.PersonaService;

import java.util.List;

@Path("/personas") //anotaciones para PATH, formatos JSON define el path/ base personas
@Produces(MediaType.APPLICATION_JSON)// produce respuestas JSON
@Consumes(MediaType.APPLICATION_JSON)// consume REquest con JSON
public class PersonaResource {

    @Inject
    private PersonaService personaService;

    //endpoint GET para listar pesonas
    @GET//anotacion para el metodo get
    public List<Persona> listarPersonas() {
        return personaService.listarPersonas(); // llama al servicio para listar
    }

    //enpoint post para agregar persona
    @POST
    public Response agregarPersona(Persona persona) {
        personaService.registrarPersona(persona);
        return Response.status(Response.Status.CREATED).entity(persona).build();//respuesta 201 con entidad
    }

    //enpoint put para modificar persona
    @PUT
    @Path("{id}") // path con parametro id
    public Response modificarPersona(@PathParam("id") Integer id, Persona persona) {
        persona.setIdPersona(id); // asigna el id de la persona
        personaService.actualizarPersona(persona);// actualiza via servicio
        return Response.ok(persona).build();// respuesta 200 con entidad
    }

    //enpoint delete para eliminar persona
    @DELETE
    @Path("{id}")//path con parametro id
    public Response eliminarPersona(@PathParam("id") Integer id) {
        Persona persona = new Persona();// crea instancia persona
        persona.setIdPersona(id);//asigna id
        personaService.eliminarPersona(persona); ///elimina via servicio
        return Response.ok(persona).build();// respuesta 204 sin contenido
    }


}
