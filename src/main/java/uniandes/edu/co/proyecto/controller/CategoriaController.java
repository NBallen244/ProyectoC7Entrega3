package uniandes.edu.co.proyecto.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.modelo.Categoria;
import uniandes.edu.co.proyecto.repository.CategoriaRepository;


@RestController
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categorias")
    public ResponseEntity<Collection<Categoria>> categorias() {
        try {
            Collection<Categoria> categorias = categoriaRepository.buscarCategorias();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/categorias/{codigo}")
    public ResponseEntity<Categoria> categorias(@PathVariable("codigo")int codigo) {
        try {
            List<Categoria> categorias = categoriaRepository.buscarCategoriaPorId(codigo);
            return ResponseEntity.ok(categorias.get(0));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/categorias/consulta")
    public ResponseEntity<Collection<Categoria>> categorias(@RequestParam(required=true)String nombre) {
        try {
            Collection<Categoria> categorias = categoriaRepository.buscarCategoriaPorNombre(nombre);
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/categorias/new/save")
    public ResponseEntity<?> categoriaGuardar(@RequestBody Categoria categoria) {
        try {
            if (categoria.getNombre() == null || categoria.getDescripcion() == null || categoria.getCaracteristicas() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan campos obligatorios");
            }
            if (categoriaRepository.buscarCategoriaPorNombre(categoria.getNombre()).size() > 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe una categoria con ese nombre");
            }
            categoriaRepository.insertarCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body("Categoria creada exitosamente");
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la categoria", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
