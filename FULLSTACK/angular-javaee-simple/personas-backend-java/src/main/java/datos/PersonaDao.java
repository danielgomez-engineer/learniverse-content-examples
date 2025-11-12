package datos;

import domain.Persona;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
