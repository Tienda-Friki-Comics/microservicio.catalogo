package com.tiendafriki.catalogo.controller;

import java.util.List;

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
    public List<Editorial> listar() {

        return Service.listar();

    }

    // -- GET: BUSCAR POR ID -- //

    @GetMapping("/buscarxid/{id}")
    public Editorial buscarPorId(@PathVariable Integer id) {

        return Service.buscarPorId(id);

    }

    // -- GET: BUSCAR POR NOMBRE -- //

    @GetMapping("/nombre/{nombre}")
    public Editorial buscarPorNombre(@PathVariable String nombre) {

        return Service.buscarPorNombre(nombre);

    }

    // -- POST: CREAR EDITORIAL -- //
    
    @PostMapping("/crear")
    public ResponseEntity<String> Crear(@Valid @RequestBody Editorial editorial) {

    String mensaje = Service.guardar(editorial);
    return ResponseEntity.status(201).body(mensaje);
}
    
    // -- DELETE : ELIMINAR CATEGORIA -- //

    @DeleteMapping("/eliminarxid/{id}")
    public ResponseEntity<String> Eliminar(@PathVariable Integer id) {
        
        String mensaje = Service.eliminar(id);
        return ResponseEntity.ok(mensaje);

    }

    // -- PUT: ACTUALIZAR CATEGORIA -- //

    @PutMapping("/actualizarxid/{id}")
    public ResponseEntity<String> Actualizar(@PathVariable Integer id, @Valid @RequestBody Editorial editorial) {

        String mensaje = Service.actualizar(id, editorial);
        return ResponseEntity.ok(mensaje);
        
    }

}
