package uniandes.edu.co.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.proyecto.modelo.Almacenaje;

public interface AlmacenajeRepository extends MongoRepository<Almacenaje, Integer>{

}
