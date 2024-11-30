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
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.modelo.Proveedor;
import uniandes.edu.co.proyecto.repositorio.ProveedorRepository;


@RestController
public class ProveedoresController {

    @Autowired
    private ProveedorRepository proveedorRepository;


    @GetMapping("/proveedores")
    public ResponseEntity<Collection<Proveedor>> proveedores() {
        try {
            Collection<Proveedor> proveedores = proveedorRepository.darProveedores();
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/proveedores/new/save")
    public ResponseEntity<String> proveedorGuardar(@RequestBody Proveedor proveedor) {
        try {
            proveedorRepository.insertarProveedor(proveedor.getNIT(), proveedor.getNombre(), proveedor.getContacto(), proveedor.getTel_contacto(), proveedor.getDireccion());
            return new ResponseEntity<>("Proveedor creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear al proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/proveedores/{nit}/edit/save")
    public ResponseEntity<String> proveedorEditarGuardar(@PathVariable("nit") Long nit, @RequestBody Proveedor proveedor) {
        try {
            proveedorRepository.actualizarProveedor(nit, proveedor.getNombre(), proveedor.getContacto(), proveedor.getTel_contacto(), proveedor.getDireccion());
            return new ResponseEntity<>("Proveedor actualizado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar al proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
