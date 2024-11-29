package uniandes.edu.co.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.proyecto.modelo.Producto;

public interface ProductoRepository extends MongoRepository<Producto, Integer>{

}
