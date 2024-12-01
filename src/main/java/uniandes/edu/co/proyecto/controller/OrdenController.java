package uniandes.edu.co.proyecto.controller;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.modelo.Orden;
import uniandes.edu.co.proyecto.modelo.ProductosOrden;
import uniandes.edu.co.proyecto.modelo.Proveedor;
import uniandes.edu.co.proyecto.modelo.Secuencia;
import uniandes.edu.co.proyecto.repository.OrdenRepository;
import uniandes.edu.co.proyecto.repository.ProveedorRepository;
import uniandes.edu.co.proyecto.repository.SucursalRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private MongoOperations mongoOperations;

    public int generateSequence(String seqName) {
        Secuencia counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
        new Update().inc("seq",1), options().returnNew(true).upsert(true),
        Secuencia.class);
    return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    @GetMapping("/ordenes")
    public Collection<Orden> getOrdenes() {
        return ordenRepository.buscarOrdenes();
    }

    @GetMapping("/ordenes/{id}")
    public ResponseEntity<?> getDetallesOrden(@PathVariable("id") int id) {
        try {
            List<Orden> ordenes = ordenRepository.buscarPorId(id);
            if (ordenes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orden no encontrada");
            }
            Orden orden = ordenes.get(0);

            return ResponseEntity.ok(orden);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/ordenes/new/save")
    public ResponseEntity<String> ordenGuardar(@RequestBody Orden norden, @RequestParam(required = true) String productos, @RequestParam(required = true) String precios, @RequestParam(required = true) String cantidades) {
        try{
            int[] id_productos= Arrays.stream(productos.split(",")).mapToInt(f -> Integer.parseInt(f)).toArray();
            int[] val_precios= Arrays.stream(precios.split(",")).mapToInt(f -> Integer.parseInt(f)).toArray();
            int[] val_cantidades= Arrays.stream(cantidades.split(",")).mapToInt(f -> Integer.parseInt(f)).toArray();

            if (id_productos.length!=val_precios.length||id_productos.length!=val_cantidades.length){
                return new ResponseEntity<>("Error al crear la orden. Cantidad de productos no concuerda con cantidad de precios/cantidades", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (norden.getProveedor()==0||norden.getSucursal_destino()==0||norden.getEstado()==null||norden.getFecha_estimada()==null){
                return new ResponseEntity<>("Proveedor inválido", HttpStatus.CREATED);
            }
            norden.setFechaCreacion(new Date());
            if (norden.getFecha_estimada().compareTo(norden.getFecha_creacion())<0){
                return new ResponseEntity<>("Fecha estimada inválida, debe ser posterior a la actual", HttpStatus.CREATED);
            }
            if (proveedorRepository.buscarProveedorPorId(norden.getProveedor()).isEmpty()){
                return new ResponseEntity<>("Proveedor no encontrado", HttpStatus.CREATED);
            }
            if (sucursalRepository.buscarSucursalPorId(norden.getSucursal_destino()).isEmpty()){
                return new ResponseEntity<>("Sucursal no encontrada", HttpStatus.CREATED);
            }

            Proveedor proveedor=proveedorRepository.buscarProveedorPorId(norden.getProveedor()).get(0);
            List<Integer> ofertas=proveedor.getProductos();
            for (int i:id_productos){
                if (!ofertas.contains(i)){
                    return new ResponseEntity<>(String.format("Orden cancelada. Producto %d no ofrecido por el proveedor selecionado", i), HttpStatus.CREATED);
                }
            }
            for (int i:val_precios){
                if (i<=0){
                    return new ResponseEntity<>("Orden cancelada. Precios no pueden ser iguales o menores a 0", HttpStatus.CREATED);
                }
            }
            for (int i:val_cantidades){
                if (i<=0){
                    return new ResponseEntity<>("Orden cancelada. Pedir 1 o más unidades de cada producto", HttpStatus.CREATED);
                }
            }

            List<ProductosOrden> productosOrden=new ArrayList<>();
            
            for (int i=0;i<id_productos.length;i++){
                ProductosOrden producto=new ProductosOrden(id_productos[i], val_precios[i], val_cantidades[i]);
                productosOrden.add(producto);
            }
            norden.setProductos(productosOrden);
            norden.setId(generateSequence(Orden.SEQUENCE_NAME));
            norden.setEstado("Vigente");
            ordenRepository.insertarOrden(norden);
            return new ResponseEntity<>("Orden creada exitosamente", HttpStatus.CREATED);
            

        }
        catch(Exception e){
            return new ResponseEntity<>("Error al crear la orden", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
