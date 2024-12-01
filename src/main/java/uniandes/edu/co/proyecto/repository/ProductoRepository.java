package uniandes.edu.co.proyecto.repository;

import java.util.Date;
import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.proyecto.modelo.Producto;

public interface ProductoRepository extends MongoRepository<Producto, Integer>{

    default void insertarProducto(Producto producto){
        save(producto);
    }

    @Query("{_id:?0 }")
    List<Producto> buscarProductoPorId(int id);

    @Query("{}")
    List<Producto> buscarProductos();

    @Query("{nombre:?0 }")
    List<Producto> buscarProductoPorNombre(String nombre);

    @Query("{fecha_vencimiento:{$lt:ISODate(?0)}}")
    List<Producto> buscarFechaMaxima(String fecha);

    @Query("{fecha_vencimiento:{$gt:ISODate(?0)}}")
    List<Producto> buscarFechaMinima(String fecha);

    @Query("{precio_unidad:{$gt:?0, $lt:?1}}")
    List<Producto> buscarRangoPrecios(int minimo, int maximo);

    @Query("{categoria:?0 }")
    List<Producto> buscarProductoPorCategoria(int categoria);

    @Query("{_id:?0 }")
    @Update("{$set:{nombre:?1, costo_bodega:?2, precio_unitario:?3, presentacion:?4, cantidad_presentacion:?5, unidad_medida:?6, peso:?7, volumen:?8, fecha_vencimiento:?9, categoria:?10}}")
    void actualizarProducto(int cod_barras, String nombre, int costoBodega, int precioUnitario, String presentacion, int cantidadPresentacion, String unidadMedida, int peso, int volumen, Date fecha_vencimiento, int categoria);

}
