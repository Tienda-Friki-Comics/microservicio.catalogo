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
    public  List<Categoria> listar() {

        return Service.listar();

    }


    // -- GET: BUSCAR POR ID -- //

    @GetMapping("/buscarxid/{id}")
    public Categoria buscarPorId(@PathVariable Integer id) {

        return Service.buscarPorId(id);

    }

    // -- GET: BUSCAR POR ID -- //

    @GetMapping("/nombre/{nombre}")
    public Categoria buscarPorNombre(@PathVariable String nombre) {

        return Service.buscarPorNombre(nombre);

    }

    // -- POST: CREAR CATEGORIA -- //
    
    @PostMapping("/crear")
    public ResponseEntity<String> Crear(@Valid @RequestBody Categoria categoria) {

        String mensaje = Service.guardar(categoria);

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
    public ResponseEntity<String> Actualizar(@PathVariable Integer id, @Valid @RequestBody Categoria categoria) {

        String mensaje = Service.actualizar(id, categoria);

        return ResponseEntity.ok(mensaje);
        
    }

}