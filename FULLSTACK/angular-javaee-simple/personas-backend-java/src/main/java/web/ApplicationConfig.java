package web;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/webservice")//anotacion para definir path base de la API REST
public class ApplicationConfig extends Application {
    //clase vacia que configura el contexto /webservice
}
