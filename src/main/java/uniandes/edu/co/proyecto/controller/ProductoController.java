package uniandes.edu.co.proyecto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.modelo.Producto;
import uniandes.edu.co.proyecto.repositorio.ProductoRepository;


@RestController
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    
    @GetMapping("/productos")
    public Collection<Producto> producto(){
        return productoRepository.darProductos();
    }

     @GetMapping("/productos/{identificador}")
     public ResponseEntity<?> obtenerProducto(@PathVariable int identificador) {
         Producto producto = productoRepository.darProducto(identificador);
     
         // Devolver si existe el producto
         if (producto != null) {
             return new ResponseEntity<>(producto, HttpStatus.OK);
         } else {
             return new ResponseEntity<>("Producto con identificador " + identificador + " no encontrado.", HttpStatus.NOT_FOUND);
         }
     }

    @PostMapping("/productos/new/save")
    public ResponseEntity<String> productoGuardar(@RequestBody Producto producto){
        
        try{
            productoRepository.insertarProducto(producto.getNombre(), producto.getCostoEnBodega(), producto.getPresentacion(), producto.getCantidadPresentacion(), producto.getUnidadMedida(), producto.getVolumenEmpaque(), producto.getPesoEmpaque(), producto.getFechaExpiracion(), producto.getCodigoDeBarras(), producto.getClasificacionCategoria().getCodigo());
            return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error al crear el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/productos/{identificador}/edit/save")
    public ResponseEntity<String> productoEditarGuardar(@PathVariable("identificador") Integer identificador, @RequestBody Producto producto) {
        try {
            // Verificar si el producto con el identificador existe
            if (!productoRepository.existeProducto(identificador)) {
                return new ResponseEntity<>("El producto con identificador " + identificador + " no existe", HttpStatus.NOT_FOUND);
            }
    
            // Verificar que ninguno de los campos obligatorios sea null
            if (producto.getNombre() == null || producto.getCostoEnBodega() == null || producto.getPresentacion() == null ||
                producto.getCantidadPresentacion() == null || producto.getUnidadMedida() == null || 
                producto.getVolumenEmpaque() == null || producto.getPesoEmpaque() == null ||
                producto.getFechaExpiracion() == null || producto.getCodigoDeBarras() == null ||
                producto.getClasificacionCategoria() == null || producto.getClasificacionCategoria().getCodigo() == null) {
    
                return new ResponseEntity<>("Uno o más campos obligatorios están vacíos o nulos.", HttpStatus.BAD_REQUEST);
            }
    
            // Actualizar el producto
            productoRepository.actualizarProducto(identificador, producto.getNombre(), producto.getCostoEnBodega(), 
                                                  producto.getPresentacion(), producto.getCantidadPresentacion(), 
                                                  producto.getUnidadMedida(), producto.getVolumenEmpaque(), 
                                                  producto.getPesoEmpaque(), producto.getFechaExpiracion(), 
                                                  producto.getCodigoDeBarras(), producto.getClasificacionCategoria().getCodigo());
    
            return new ResponseEntity<>("Producto actualizado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("productos/filtrados")
    public Collection<Producto> productosfiltrados(
        @RequestParam(required = false) Double precioMinU,
        @RequestParam(required = false) Double precioMaxU,
        @RequestParam(required = false) String fechaSuperiorU,
        @RequestParam(required = false) String fechaInferiorU,
        @RequestParam(required = false) Integer sucursalIdU,
        @RequestParam(required = false) String categoriaNombreU
    ){
        return productoRepository.darProductosFiltrados(precioMinU, precioMaxU, fechaSuperiorU, fechaInferiorU, sucursalIdU, categoriaNombreU);
    }

    @GetMapping("/productos/productosEnBodega")
    public Collection<Map<String, Object>> obtenerProductosEnBodega(
            @RequestParam Integer idSucursal,
            @RequestParam Integer idBodega) {
        Collection<Object[]> resultado = productoRepository.darProductosBodega(idSucursal, idBodega);
        
        Collection<Map<String, Object>> listaproductos = new ArrayList<>();
        for (Object[] fila : resultado) {
            Map<String, Object> productos = new HashMap<>();
            productos.put("id", fila[0]);
            productos.put("nombre", fila[1]);
            productos.put("nivelMinimoReorden", fila[2]);
            productos.put("costoPromedio", fila[3]);
            productos.put("cantidadEnBodega", fila[4]);
            listaproductos.add(productos);
        }
        return listaproductos;
    }

    //Para RCF5:
    @GetMapping("/productosParaOrdenDeCompra")
    public Collection<Map<String, Object>> obtenerProductosParaOrdenDeCompra() {
    Collection<Object[]> resultado = productoRepository.obtenerProductosBajoNivelReorden();
    
    // Convertir los resultados en una colección de Map<String, Object> para mayor claridad en el retorno.
    Collection<Map<String, Object>> productosParaOrdenCompra = new ArrayList<>();
    for (Object[] fila : resultado) {
        Map<String, Object> producto = new HashMap<>();
        producto.put("identificador", fila[0]);
        producto.put("nombreProducto", fila[1]);
        producto.put("nombreBodegaNivelBajo", fila[2]);
        producto.put("nombreSucursalAsociada", fila[3]);
        producto.put("nombrePosibleProveedor", fila[4]);
        producto.put("cantidadActualEnBodega", fila[5]);
        producto.put("nivelMinimoReorden", fila[6]);
        productosParaOrdenCompra.add(producto);
    }
    return productosParaOrdenCompra;
    }


}
