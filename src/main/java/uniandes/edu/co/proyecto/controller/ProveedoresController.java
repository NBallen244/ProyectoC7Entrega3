package uniandes.edu.co.proyecto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import uniandes.edu.co.proyecto.repository.ProveedorRepository;



@RestController
public class ProveedoresController {

    @Autowired
    private ProveedorRepository proveedorRepository;


    @GetMapping("/proveedores")
    public ResponseEntity<Collection<Proveedor>> proveedores() {
        try {
            Collection<Proveedor> proveedores = proveedorRepository.buscaProveedores();
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/proveedores/new/save")
    public ResponseEntity<String> proveedorGuardar(@RequestBody Proveedor proveedor) {
        try {
            if (proveedor.getNombre() == null || proveedor.getDireccion() == null || proveedor.getContacto() == null || proveedor.getTel_contacto() == 0||proveedor.getNit() <= 0) {
                return new ResponseEntity<>("Faltan datos", HttpStatus.BAD_REQUEST);
            }
            List<Proveedor> prueba = proveedorRepository.buscarProveedorPorId(proveedor.getNit());
            if (prueba.size() > 0) {
                return new ResponseEntity<>("El proveedor con nit definido ya existe", HttpStatus.BAD_REQUEST);
            }
            List<Integer> productosActuales = new ArrayList<Integer>();
            for(int i: proveedor.getProductos()){
                if (productosActuales.contains(i)) {
                    return new ResponseEntity<>("El proveedor no puede tener productos repetidos", HttpStatus.BAD_REQUEST);
                }
                else{
                    productosActuales.add(i);
                }
            }
            return new ResponseEntity<>("Proveedor creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear al proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/proveedores/{nit}/edit/save")
    public ResponseEntity<String> proveedorEditarGuardar(@PathVariable("nit") int nit, @RequestBody Proveedor proveedor) {
        try {
            if (proveedor.getNombre() == null || proveedor.getDireccion() == null || proveedor.getContacto() == null || proveedor.getTel_contacto() == 0||proveedor.getNit() <= 0) {
                return new ResponseEntity<>("Faltan datos", HttpStatus.BAD_REQUEST);
            }
            List<Proveedor> prueba = proveedorRepository.buscarProveedorPorId(nit);
            if (prueba.size() == 0) {
                return new ResponseEntity<>("El proveedor con nit definido no existe. Revise o cree un nuevo proveedor", HttpStatus.BAD_REQUEST);
            }
            List<Integer> productosActuales = new ArrayList<Integer>();
            for(int i: proveedor.getProductos()){
                if (productosActuales.contains(i)) {
                    return new ResponseEntity<>("El proveedor no puede tener productos repetidos", HttpStatus.BAD_REQUEST);
                }
                else{
                    productosActuales.add(i);
                }
            }
            proveedorRepository.actualizarProveedor(proveedor.getNit(), proveedor.getNombre(), proveedor.getDireccion(), proveedor.getContacto(), proveedor.getTel_contacto(), proveedor.getProductos());
            return new ResponseEntity<>("Proveedor actualizado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar al proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
