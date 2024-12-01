package uniandes.edu.co.proyecto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.proyecto.modelo.Almacenaje;

public interface AlmacenajeRepository extends MongoRepository<Almacenaje, Integer>{

    default void insertarAlmacenaje(Almacenaje almacenaje){
        save(almacenaje);
    }

    @Query("{sucursal:?0, bodega:?1} }")
    List<Almacenaje> buscarInventarioPorSucursalyBodega(int sucursal, int bodega);

    @Query("{sucursal:?0}")
    List<Almacenaje> buscarInventarioPorSucursal(int sucursal);
    

}
