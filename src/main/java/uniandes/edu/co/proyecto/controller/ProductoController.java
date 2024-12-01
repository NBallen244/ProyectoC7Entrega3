package uniandes.edu.co.proyecto.controller;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.modelo.Producto;
import uniandes.edu.co.proyecto.modelo.Secuencia;
import uniandes.edu.co.proyecto.repository.CategoriaRepository;
import uniandes.edu.co.proyecto.repository.ProductoRepository;
import uniandes.edu.co.proyecto.repository.ProductosSucursalRepo;


@RestController
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductosSucursalRepo repoConsulta;

    

    @Autowired
    private MongoOperations mongoOperations;

    public int generateSequence(String seqName) {
        Secuencia counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
        new Update().inc("seq",1), options().returnNew(true).upsert(true),
        Secuencia.class);
    return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    @GetMapping("/productos")
    public Collection<Producto> getProductos() {
        return productoRepository.buscarProductos();
    }

    /*RFC1 */
    @GetMapping("/productos/consulta")
    public ResponseEntity<Collection<?>> getProductosFiltro(@RequestParam(required = false) String filtro, 
                                                    @RequestParam(required = false) Integer sucursal,
                                                    @RequestParam(required = false) String fecha,
                                                    @RequestParam(required = false) Integer precioMin,
                                                    @RequestParam(required = false) Integer precioMax,
                                                    @RequestParam(required = false) Integer categoria) throws ParseException{
        Collection<?> response;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        if (filtro.isEmpty()){response=productoRepository.buscarProductos();}
        else if (filtro.equals("sucursal") && sucursal!=null){response=repoConsulta.obtenerProductosSucursal(sucursal);}
        else if (filtro.equals("fechaMax") && fecha!=null){response=productoRepository.buscarFechaMaxima(formatter.parse(fecha));}
        else if (filtro.equals("fechaMin") && fecha!=null){response=productoRepository.buscarFechaMinima(formatter.parse(fecha));}
        else if (filtro.equals("categoria") && categoria!=null){response=productoRepository.buscarProductoPorCategoria(categoria);}
        else if (filtro.equals("rangoPrecio")){response=productoRepository.buscarRangoPrecios(precioMin, precioMax);}
        else{
            response=productoRepository.buscarProductos();}
        return ResponseEntity.ok(response);
    }

    @GetMapping("/productos/{codigo}")
    public ResponseEntity<Producto> getProducto(@PathVariable("codigo")int codigo) {
        try {
            Producto producto = productoRepository.buscarProductoPorId(codigo).get(0);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/productos/consultaNombre")
    public ResponseEntity<Collection<Producto>> getProductosNombre(@RequestParam(required=true)String nombre) {
        try {
            Collection<Producto> producto = productoRepository.buscarProductoPorNombre(nombre);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/productos/new/save")
    public ResponseEntity<String> productoGuardar(@RequestBody Producto producto) {
        try{
            if (productoRepository.buscarProductoPorNombre(producto.getNombre()).size() > 0){
                return new ResponseEntity<>("El producto ya existe", HttpStatus.CONFLICT);
            }
            if (categoriaRepository.buscarCategoriaPorId(producto.getCategoria()).size() == 0){
                return new ResponseEntity<>("La categoria no existe", HttpStatus.BAD_REQUEST);
            }
            if (producto.getCosto_bodega() <= 0 || producto.getPrecio_unitario() <= 0 || producto.getPeso() <= 0 || producto.getVolumen() <= 0 || producto.getCantidad_presentacion() <= 0|| producto.getCategoria() <= 0 || producto.getFecha_vencimiento() == null || producto.getNombre() == null || producto.getPresentacion() == null || producto.getUnidad_medida() == null){
                return new ResponseEntity<>("Valores faltantes", HttpStatus.BAD_REQUEST);
            }
            producto.setCodigoBarras(generateSequence(Producto.SEQUENCE_NAME));
            productoRepository.insertarProducto(producto);
            return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al crear el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/productos/{cod_barras}/edit/save")
    public ResponseEntity<?> ordenEditarGuardar(@PathVariable("cod_barras")int cod_barras, @RequestBody Producto producto){
        try{
            if (productoRepository.buscarProductoPorId(cod_barras).size() == 0){
                return new ResponseEntity<>("El producto no existe", HttpStatus.NOT_FOUND);
            }
            if (categoriaRepository.buscarCategoriaPorId(producto.getCategoria()).size() == 0){
                return new ResponseEntity<>("La categoria no existe", HttpStatus.BAD_REQUEST);
            }
            if (producto.getCosto_bodega() <= 0 || producto.getPrecio_unitario() <= 0 || producto.getPeso() <= 0 || producto.getVolumen() <= 0 || producto.getCantidad_presentacion() <= 0|| producto.getCategoria() <= 0 || producto.getFecha_vencimiento() == null || producto.getNombre() == null || producto.getPresentacion() == null || producto.getUnidad_medida() == null){
                return new ResponseEntity<>("Valores faltantes", HttpStatus.BAD_REQUEST);
            }
            productoRepository.actualizarProducto(cod_barras, producto.getNombre(), producto.getCosto_bodega(), producto.getPrecio_unitario(), producto.getPresentacion(), producto.getCantidad_presentacion(), producto.getUnidad_medida(), producto.getPeso(),  producto.getVolumen(),  producto.getFecha_vencimiento(), producto.getCategoria());
            return new ResponseEntity<Producto>(producto, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al actualizar el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
