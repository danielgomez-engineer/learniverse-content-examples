package servicio;

import datos.PersonaDao;
import domain.Persona;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

/*Cada vez que un cliente llama al bean, el contenedor (WildFly en tu caso) le entrega una instancia cualquiera del pool.
No conserva datos de una llamada a otra.
Es ideal para lógica de negocio reutilizable, servicios transaccionales, o acceso a base de datos.*/
// Anotacion para EJB stateless
@Stateless
public class PersonaService {

    // inyeccion del DAO
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
