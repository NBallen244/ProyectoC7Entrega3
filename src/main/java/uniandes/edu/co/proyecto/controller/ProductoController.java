package uniandes.edu.co.proyecto.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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
import uniandes.edu.co.proyecto.repositorio.ProductoRepository;
import uniandes.edu.co.proyecto.repositorio.ProductoRepository.RespuestaInsuficiente;

@RestController
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/productos")
    public Collection<Producto> getProductos() {
        return productoRepository.darProductos();
    }

    /*RFC2 */
    @GetMapping("/productos/consulta")
    public ResponseEntity<Collection<Producto>> getProductosFiltro(@RequestParam(required = false) String filtro, 
                                                    @RequestParam(required = false) Long sucursal,
                                                    @RequestParam(required = false) String fecha,
                                                    @RequestParam(required = false) Integer precioMin,
                                                    @RequestParam(required = false) Integer precioMax,
                                                    @RequestParam(required = false) Long categoria){
        Collection<Producto> response;
        if (filtro.isEmpty()){response=productoRepository.darProductos();}
        else if (filtro.equals("sucursal") && sucursal!=null){response=productoRepository.darProductoSucursal(sucursal);}
        else if (filtro.equals("fechaMax") && fecha!=null){response=productoRepository.darProductoAnterior(fecha);}
        else if (filtro.equals("fechaMin") && fecha!=null){response=productoRepository.darProductoPosterior(fecha);}
        else if (filtro.equals("categoria") && categoria!=null){response=productoRepository.darProductoCategoria(categoria);}
        else if (filtro.equals("rangoPrecio")){response=productoRepository.darProductoRangoPrecios(precioMin, precioMax);}
        else{
            response=productoRepository.darProductos();}
        return ResponseEntity.ok(response);
    }

    @GetMapping("/productos/{codigo}")
    public ResponseEntity<Producto> getProducto(@PathVariable("codigo")int codigo) {
        try {
            Producto producto = productoRepository.darProducto(codigo);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /*RFC5 */
    @GetMapping("/productos/insuficientes")
    public ResponseEntity<Collection<RespuestaInsuficiente>> getProductosInsuficientes() {
        try {
            Collection<RespuestaInsuficiente> productos = productoRepository.darProductoInsuficiente();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/productos/consultaNombre")
    public ResponseEntity<Collection<Producto>> getProductosNombre(@RequestParam(required=true)String nombre) {
        try {
            Collection<Producto> producto = productoRepository.darProductoPorNombre(nombre);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/productos/new/save")
    public ResponseEntity<String> productoGuardar(@RequestBody Producto producto) {
        try{
            productoRepository.insertarProducto(producto.getNombre(), producto.getCosto_bodega(), producto.getPrecio_unitario(), producto.getPresentacion(), producto.getPeso(),  producto.getVolumen(), producto.getUnidad_medida(), producto.getCantidad_presentacion(),  producto.getFecha_vencimiento(), producto.getCategoria().getCodigo());
            return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al crear el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/productos/{cod_barras}/edit/save")
    public ResponseEntity<String> ordenEditarGuardar(@PathVariable("cod_barras")Long cod_barras, @RequestBody Producto producto){
        try{
            productoRepository.actualizarProducto(cod_barras, producto.getNombre(), producto.getCosto_bodega(), producto.getPrecio_unitario(), producto.getPresentacion(), producto.getPeso(),  producto.getVolumen(), producto.getUnidad_medida(), producto.getCantidad_presentacion(),  producto.getFecha_vencimiento(), producto.getCategoria().getCodigo());
            return new ResponseEntity<>("Producto actualizado exitosamente", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al actualizar el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
