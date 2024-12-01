package uniandes.edu.co.proyecto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.proyecto.modelo.Categoria;

public interface CategoriaRepository extends MongoRepository<Categoria, Integer>{
    default void insertarCategoria(Categoria categoria){
        save(categoria);
    }

    @Query("{_id:?0 }")
    List<Categoria> buscarCategoriaPorId(int id);

    @Query("{nombre:?0 }")
    List<Categoria> buscarCategoriaPorNombre(String nombre);

    @Query("{}") 
    List<Categoria> buscarCategorias();
}
