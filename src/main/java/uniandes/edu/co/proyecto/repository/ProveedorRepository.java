package uniandes.edu.co.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.proyecto.modelo.Proveedor;

public interface ProveedorRepository extends MongoRepository<Proveedor, Integer>{

}
