package uniandes.edu.co.proyecto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.proyecto.modelo.Proveedor;

public interface ProveedorRepository extends MongoRepository<Proveedor, Integer>{

    default void insertarProveedor(Proveedor proveedor){
        save(proveedor);
    }

    @Query("{_id:?0 }")
    List<Proveedor> buscarProveedorPorId(int id);

    @Query("{}")
    List<Proveedor> buscaProveedores();

    @Query("{_id:?0, productos:{$all:[?1]}  }")
    List<Proveedor> buscarProveedorConProducto(int id, int idProducto);

    @Query("{_id:?0}")
    @Update("{$set:{nombre:?1, direccion:?2, contacto:?3, tel_contacto:?4, productos:?5}}")
    void actualizarProveedor(int nit, String nombre, String direccion, String contacto, int tel_contacto, List<Integer> productos);

}
