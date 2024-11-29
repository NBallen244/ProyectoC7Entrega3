package uniandes.edu.co.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.*;

import uniandes.edu.co.proyecto.modelo.Bodega;
import uniandes.edu.co.proyecto.modelo.Sucursal;

public interface SucursalRepository  extends MongoRepository<Sucursal, Integer>{

    //CREAR SUCURSAL (NO ES NECESARIO PQ EL REPO YA TIENE SAVE)
    default void insertarSucursal(Sucursal sucursal){
        save(sucursal);
    }

   
