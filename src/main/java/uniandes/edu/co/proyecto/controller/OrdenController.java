package uniandes.edu.co.proyecto.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.modelo.Orden;
import uniandes.edu.co.proyecto.modelo.ProductosOrden;
import uniandes.edu.co.proyecto.repositorio.OfertaRepository;
import uniandes.edu.co.proyecto.repositorio.OfertaRepository.ProductosProveedor;
import uniandes.edu.co.proyecto.repositorio.OrdenRepository;
import uniandes.edu.co.proyecto.repositorio.ProductosOrdenRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private ProductosOrdenRepository productosOrdenRepository;

    @GetMapping("/ordenes")
    public Collection<Orden> getOrdenes() {
        return ordenRepository.darOrdenes();
    }

    @GetMapping("/ordenes/{id}")
    public ResponseEntity<?> getDetallesOrden(@PathVariable("id") Long id) {
        try {
            Collection<ProductosOrden> productos = productosOrdenRepository.darProductosXOrden(id);
            Orden orden=ordenRepository.darOrden(id);
            Map<String, Object> response = new HashMap<>();
            response.put("orden", orden);
            response.put("Productos", productos);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/ordenes/new/save")
    public ResponseEntity<String> ordenGuardar(@RequestBody Orden norden, @RequestParam(required = true) String productos, @RequestParam(required = true) String precios, @RequestParam(required = true) String cantidades) {
        try{
            long[] id_productos= Arrays.stream(productos.split(",")).mapToLong(f -> Long.parseLong(f)).toArray();
            long[] val_precios= Arrays.stream(precios.split(",")).mapToLong(f -> Long.parseLong(f)).toArray();
            long[] val_cantidades= Arrays.stream(cantidades.split(",")).mapToLong(f -> Long.parseLong(f)).toArray();
            norden.setFechaCreacion(new Date(new java.util.Date().getTime()));
            if (norden.getFecha_estimada().compareTo(norden.getFecha_creacion())<0){
                return new ResponseEntity<>("Fecha inválida, debe ser posterior a la actual", HttpStatus.CREATED);
            }
            int productosValidos=0;
            String productos_rechazados=" ";
            List<Long> id_productosP= new ArrayList<Long>();

            ordenRepository.insertarOrden(norden.getFecha_creacion(), norden.getFecha_estimada(), norden.getProveedor().getNIT(), norden.getSucursal_destino().getId());
            Orden creada=ordenRepository.darUltimaOrden();
            Collection<ProductosProveedor> productosProveedores = ofertaRepository.darProductosXproveedor(norden.getProveedor().getNIT());
            for(ProductosProveedor pp: productosProveedores){
                id_productosP.add(pp.getP());
            }

            for(int i=0; i<id_productos.length; i++){
                long idP=id_productos[i];
                if(id_productosP.contains(idP)){
                    productosValidos++;
                    productosOrdenRepository.insertarProductosOrden(creada.getId(), idP, val_cantidades[i], val_precios[i]);
                }
                else{
                    productos_rechazados+=idP+", ";
                }
            }

            if (productosValidos==0){
                ordenRepository.eliminarOrden(creada.getId());
                return new ResponseEntity<>("Productos no ofrecidos por el proveedor seleccionado", HttpStatus.CREATED);}
            else{
                return new ResponseEntity<>("Orden creada. Los siguientes productos no se incluyeron por falta de disponibilidad con el proveedor: "+productos_rechazados, HttpStatus.CREATED);}
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al crear la orden", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    @PutMapping("/ordenes/{id}/edit/anular/save")
    public ResponseEntity<String> ordenAnularGuardar(@PathVariable("id")Long id){
        try{
            Orden orden=ordenRepository.darOrden(id);
            if(orden.getEstado().equals("entregada")){
                return new ResponseEntity<>("Error al anular la orden-orden entregada", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(orden.getEstado().equals("anulada")){
                return new ResponseEntity<>("Error al anular la orden-orden ya anulada", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            ordenRepository.actualizarOrdenAnulada(id);
            return new ResponseEntity<>("Orden anulada exitosamente", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al anular la orden-orden inexistente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/ordenes/{id}/edit/entregar/save")
    public ResponseEntity<String> ordenEntregarGuardar(@PathVariable("id")Long id){
        try{
            Orden orden=ordenRepository.darOrden(id);
            if(orden.getEstado().equals("entregada")){
                return new ResponseEntity<>("Error al entregar la orden-orden ya entregada", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(orden.getEstado().equals("anulada")){
                return new ResponseEntity<>("Error al entregar la orden-orden anulada", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            ordenRepository.actualizarOrdenAnulada(id);
            return new ResponseEntity<>("Orden entregada exitosamente", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Error al entregar la orden-orden inexistente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    


}
