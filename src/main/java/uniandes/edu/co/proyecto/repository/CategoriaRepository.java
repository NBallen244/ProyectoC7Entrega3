package uniandes.edu.co.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.proyecto.modelo.Categoria;

public interface CategoriaRepository extends MongoRepository<Categoria, Integer>{

}
