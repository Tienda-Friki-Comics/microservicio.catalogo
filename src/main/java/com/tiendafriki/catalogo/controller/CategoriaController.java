package com.tiendafriki.catalogo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendafriki.catalogo.model.Categoria;
import com.tiendafriki.catalogo.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService Service;


    // -- GET: LISTAR TODO -- //

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {

        List<Categoria> categoria = Service.listar();

        return ResponseEntity.ok(categoria);
    }


    // -- GET: BUSCAR POR ID -- //

    @GetMapping("/buscarxid/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        Optional<Categoria> categoria = Service.buscarPorId(id);

        if (categoria.isEmpty()) {

            return ResponseEntity.status(404).body("Categoria no encontrada [X_X]");
        }

        return ResponseEntity.ok(categoria);
    }

    // -- GET: BUSCAR POR ID -- //

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {

        Optional<Categoria> categoria = Service.buscarPorNombre(nombre);

        if (categoria.isEmpty()) {

            return ResponseEntity.status(404).body("Categoria no encontrada [X_X]");
        }

        return ResponseEntity.ok(categoria);
    }

    // -- POST: CREAR CATEGORIA -- //
    
    @PostMapping("/crear")
    public ResponseEntity<?> Crear(@Valid @RequestBody Categoria categoria) {

    String mensaje = Service.guardar(categoria);

    if (mensaje.toLowerCase().contains("ya existe")) {

        return ResponseEntity.badRequest().body(mensaje);
    }


    return ResponseEntity.status(201).body(mensaje);
}
    
    // -- DELETE : ELIMINAR CATEGORIA -- //

    @DeleteMapping("/eliminarxid/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Integer id) {
        
        String mensaje = Service.eliminar(id);

        if (mensaje.toLowerCase().contains("no encontrado") ||
            mensaje.toLowerCase().contains("no encontrada")) {

            return ResponseEntity.status(404).body(mensaje);
        }
        
        return ResponseEntity.ok(mensaje);

    }

    // -- PUT: ACTUALIZAR CATEGORIA -- //

    @PutMapping("/actualizar")
    public ResponseEntity<?> Actualizar(@Valid @RequestBody Categoria categoria) {

        String mensaje = Service.actualizar(categoria);

        if (mensaje.toLowerCase().contains("no encontrado") ||
            mensaje.toLowerCase().contains("no encontrada")) {
                return ResponseEntity.status(404).body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
        
    }

}