package uniandes.edu.co.proyecto.repository;

import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.proyecto.modelo.Orden;

public interface OrdenRepository extends MongoRepository<Orden, Integer>{

    //LEER ORDEN 
    @Query("{_id: ?0}")
    List<Orden> buscarPorId(int id);

    @Query("{sucursal_destino: ?0}")
    List<Orden> buscarPorSucursal(int id);

    @Query(value="{}", fields="{productos:0}")
    List<Orden> buscarOrdenes();

    default void insertarOrden(Orden orden){
        save(orden);
    }

}
