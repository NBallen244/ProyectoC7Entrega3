package uniandes.edu.co.proyecto.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.modelo.Sucursal;
import uniandes.edu.co.proyecto.repositorio.SucursalRepository;

@RestController
public class SucursalesController {
    
    @Autowired
    private SucursalRepository sucursalRepository;


    @GetMapping("/sucursales")
    public ResponseEntity<Collection<Sucursal>> sucursales() {
        try {
            Collection<Sucursal> sucursales = sucursalRepository.darSucursales();
            return ResponseEntity.ok(sucursales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*RFC4 */
    @GetMapping("/sucursales/consulta")
    public ResponseEntity<Collection<Sucursal>> sucursalesConP(@RequestParam(required = true) Long producto) {
        try {
            Collection<Sucursal> sucursales = sucursalRepository.darSucursalesConProducto(producto);
            return ResponseEntity.ok(sucursales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/sucursales/new/save")
    public ResponseEntity<String> sucursalGuardar(@RequestBody Sucursal sucursal) {
        try {
            sucursalRepository.insertarSucursal(sucursal.getNombre(), sucursal.getTamaño(), sucursal.getCiudad().getId(), sucursal.getTelefono(), sucursal.getDireccion());
            return new ResponseEntity<>("Sucursal creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la Sucursal", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
