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

import com.tiendafriki.catalogo.model.Editorial;
import com.tiendafriki.catalogo.service.EditorialService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialService Service;


    // -- GET: LISTAR TODO -- //

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {

        List<Editorial> editorial = Service.listar();

        return ResponseEntity.ok(editorial);
    }


    // -- GET: BUSCAR POR ID -- //

    @GetMapping("/buscarxid/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        Optional<Editorial> editorial = Service.buscarPorId(id);

        if (editorial.isEmpty()) {

            return ResponseEntity.status(404).body("Editorial no encontrada [X_X]");
        }

        return ResponseEntity.ok(editorial);
    }

    // -- GET: BUSCAR POR NOMBRE -- //

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {

        Optional<Editorial> editorial = Service.buscarPorNombre(nombre);

        if (editorial.isEmpty()) {

            return ResponseEntity.status(404).body("Editorial no encontrada [X_X]");
        }

        return ResponseEntity.ok(editorial);
    }

    // -- POST: CREAR EDITORIAL -- //
    
    @PostMapping("/crear")
    public ResponseEntity<?> Crear(@Valid @RequestBody Editorial editorial) {

    String mensaje = Service.guardar(editorial);

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
    public ResponseEntity<?> Actualizar(@Valid @RequestBody Editorial editorial) {

        String mensaje = Service.actualizar(editorial);

        if (mensaje.toLowerCase().contains("no encontrado") ||
            mensaje.toLowerCase().contains("no encontrada")) {
                return ResponseEntity.status(404).body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
        
    }

}
