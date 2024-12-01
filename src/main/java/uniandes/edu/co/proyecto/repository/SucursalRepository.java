package uniandes.edu.co.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.*;

import uniandes.edu.co.proyecto.modelo.Sucursal;

public interface SucursalRepository  extends MongoRepository<Sucursal, Integer>{

    default void insertarSucursal(Sucursal sucursal){
        save(sucursal);
    }

    @Query("{ _id : ?0 }")
    List<Sucursal> buscarSucursalPorId(int id);

    @Query("{}")
    List<Sucursal> buscarSucursales();

    @Query("{_id : ?0, bodegas:{numero: ?1}  }")
    List<Sucursal> buscarSucursalconBodega(int idSucursal, int idBodega);

    @Query("{_id : ?0 }")
    @Update("{'$pull': {'bodegas': {numero:?1}}}")
    void eliminarBodega(int idSucursal, int idBodega);

    @Query("{_id : ?0 }")
    @Update("{'$push': {'bodegas': {numero:?1, nombre:?2, tamaño:?3}}}")
    void crearBodega(int idSucursal, int idBodega, String nombre, int tamaño);


}

   
