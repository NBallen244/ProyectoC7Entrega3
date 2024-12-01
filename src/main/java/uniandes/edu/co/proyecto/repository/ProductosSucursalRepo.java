package uniandes.edu.co.proyecto.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

@Repository
public class ProductosSucursalRepo {

    private final MongoTemplate mongoTemplate;

    public ProductosSucursalRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Document> obtenerProductosSucursal(int sucursal){
        List<Document> pipeline= List.of(
            new Document("$match", new Document("sucursal", sucursal)),
            new Document("$unwind", "$inventarios"),
            new Document("$lookup", new Document()
            .append("from", "productos")
            .append("localField", "inventarios.producto")
            .append("foreignField", "_id")
            .append("as", "det_producto")),
            new Document("$unwind", "$det_producto"),
            new Document("$group", new Document()
            .append("_id", "$sucursal")
            .append("productos", new Document("$push", "$det_producto"))
            ),
            new Document("$project", new Document().append("_id", 0).append("sucursal", "$_id").append("productos", 1))
        );

        return mongoTemplate.getCollection("inventarios").aggregate(pipeline).into(new ArrayList<>());
    }

    public List<Document> obtenerInventarioSucursal(int sucursal){
        List<Document> pipeline= List.of(
            new Document("$match", new Document("sucursal", sucursal)),
            new Document("$unwind", "$inventarios"),
            new Document("$lookup", new Document()
            .append("from", "productos")
            .append("localField", "inventarios.producto")
            .append("foreignField", "_id")
            .append("as", "det_producto")),
            new Document("$unwind", "$det_producto"),
            new Document("$group", new Document()
            .append("_id", "$sucursal")
            .append("inventarios", new Document("$push", new Document()
            .append("producto", "$det_producto.nombre")
            .append("num_bodega", "$bodega")
            .append("cantidad", "$inventarios.cantidad")
            .append("capacidad", "$inventarios.capacidad")
            .append("costo_promedio", "$inventarios.costo_promedio")
            .append("nivel_minimo", "$inventarios.nivel_minimo")
            ))
            ),
            new Document("$project", new Document().append("_id", 0).append("sucursal", "$_id").append("inventarios", 1))
        );

        return mongoTemplate.getCollection("inventarios").aggregate(pipeline).into(new ArrayList<>());
    }
    
}
