package uniandes.edu.co.proyecto.repository;

import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.proyecto.modelo.Orden;

public interface OrdenRepository extends MongoRepository<Orden, Integer>{

    //LEER ORDEN 
    @Query("{_id: ?0}")
    List<Orden> buscarPorId(int id);

}
