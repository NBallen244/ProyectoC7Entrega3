package uniandes.edu.co.proyecto.controller;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.modelo.Almacenaje;
import uniandes.edu.co.proyecto.modelo.Bodega;
import uniandes.edu.co.proyecto.modelo.Orden;
import uniandes.edu.co.proyecto.modelo.Secuencia;
import uniandes.edu.co.proyecto.modelo.Sucursal;
import uniandes.edu.co.proyecto.repository.AlmacenajeRepository;
import uniandes.edu.co.proyecto.repository.OrdenRepository;
import uniandes.edu.co.proyecto.repository.ProductosSucursalRepo;
import uniandes.edu.co.proyecto.repository.SucursalRepository;

@RestController
public class SucursalesController {
    
    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private AlmacenajeRepository almacenajeRepository;

    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private ProductosSucursalRepo repoConsulta;

    public int generateSequence(String seqName) {
        Secuencia counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
        new Update().inc("seq",1), options().returnNew(true).upsert(true),
        Secuencia.class);
    return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }


    @GetMapping("/sucursales")
    public ResponseEntity<Collection<Sucursal>> sucursales() {
        try {
            Collection<Sucursal> sucursales = sucursalRepository.buscarSucursales();
            return ResponseEntity.ok(sucursales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/sucursales/{id}/inventario")
    public ResponseEntity<?> getInventario(@PathVariable("id") int id) {
        try {
            List<Sucursal> sucursales = sucursalRepository.buscarSucursalPorId(id);
            if (sucursales.isEmpty()) {
                return new ResponseEntity<String>("No existe la sucursal", HttpStatus.BAD_REQUEST);
            }
            Sucursal sucursal = sucursales.get(0);
            if (sucursal.getBodegas().isEmpty()) {
                return new ResponseEntity<String>("La sucursal no tiene bodegas", HttpStatus.BAD_REQUEST);
            }
            List<Almacenaje> inventario = almacenajeRepository.buscarInventarioPorSucursal(id);
            if (inventario.isEmpty()) {
                return new ResponseEntity<String>("Las bodegas de la sucursal estan vacías", HttpStatus.BAD_REQUEST);
            }

            List<Document> inventarioMap=repoConsulta.obtenerInventarioSucursal(id);
            return ResponseEntity.ok(inventarioMap);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/sucursales/new/save")
    public ResponseEntity<String> sucursalGuardar(@RequestBody Sucursal sucursal) {
        try {
            if (sucursal.getCiudad() == null || sucursal.getDireccion() == null || sucursal.getNombre() == null|| sucursal.getTamaño()<=0) {
                return new ResponseEntity<>("Faltan datos", HttpStatus.BAD_REQUEST);
            }
            else{
                List<Integer> bodegasActuales= new ArrayList<Integer>();
                for (Bodega bodega : sucursal.getBodegas()) {
                    int id=bodega.getNumero();
                    if (bodegasActuales.contains(id)) {
                        return new ResponseEntity<>("Error de creacion: Bodegas repetidas", HttpStatus.BAD_REQUEST);
                    }
                    else{
                        bodegasActuales.add(id);
                    }
                }
            }
            sucursal.setId(generateSequence(Sucursal.SEQUENCE_NAME));
            sucursalRepository.insertarSucursal(sucursal);
            return new ResponseEntity<>("Sucursal creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la Sucursal", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/sucursales/{id}/newBodega")
    public ResponseEntity<String> agregarBodega(@RequestBody Bodega bodega, @PathVariable("id") int id) {
        try {
            List<Sucursal> prueba=sucursalRepository.buscarSucursalPorId(id);
            if (prueba.isEmpty()) {
                return new ResponseEntity<>("No existe la sucursal", HttpStatus.BAD_REQUEST);
            }
            else{
                if (bodega.getNombre()==null || bodega.getTamaño()<=0) {
                    return new ResponseEntity<>("Faltan datos", HttpStatus.BAD_REQUEST);
                }
                else{
                    bodega.setNumero(prueba.get(0).getBodegas().size()+1);
                    sucursalRepository.crearBodega(id, bodega.getNumero(), bodega.getNombre(), bodega.getTamaño());
                }
            }
            return new ResponseEntity<>("Bodega creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la bodega", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PutMapping("/sucursales/{id}/deleteBodega/{idBodega}")
    public ResponseEntity<String> borrarBodega(@PathVariable("id") int id, @PathVariable("idBodega") int idBodega) {
        try {
            List<Sucursal> prueba=sucursalRepository.buscarSucursalconBodega(id, idBodega);
            if (prueba.isEmpty()) {
                return new ResponseEntity<>("La sucursal elegida no tiene bodega con dicho número", HttpStatus.BAD_REQUEST);
            }
            else{
                List<Orden> ordenes=ordenRepository.buscarPorSucursal(id);
                if (!ordenes.isEmpty()) {
                    return new ResponseEntity<>("No se puede borrar la bodega porque su sucursal tiene ordenes pendientes", HttpStatus.BAD_REQUEST);
                }
                List<Almacenaje> inventario=almacenajeRepository.buscarInventarioPorSucursalyBodega(id, idBodega);
                if (!inventario.isEmpty()) {
                    if (inventario.get(0).getInventarios().size()>0) {
                    return new ResponseEntity<>("No se puede borrar la bodega porque tiene inventario", HttpStatus.BAD_REQUEST);}
                    else{sucursalRepository.eliminarBodega(id, idBodega);}
                }
                else{
                    sucursalRepository.eliminarBodega(id, idBodega); 
                }
            }
            return new ResponseEntity<>("Bodega eliminada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la bodega", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
