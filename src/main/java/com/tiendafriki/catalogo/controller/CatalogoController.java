package com.tiendafriki.catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tiendafriki.catalogo.service.*;
import com.tiendafriki.catalogo.dto.ProductoResumenDTO;
import com.tiendafriki.catalogo.model.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService Service;

    // -- GET: LISTAR TODO -- //

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {

        List<ProductoResumenDTO> catalogo = Service.listarDTO();

        if (catalogo.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("Productos Del Catalogo No Encontrado [X_X]");
        }

        return ResponseEntity.ok(catalogo);
    }

    // -- GET: BUSCAR POR ID -- //

    @GetMapping("/buscarxid/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        Optional<ProductoResumenDTO> producto = Service.buscarPorId(id);

        if (producto.isEmpty()) {

            return ResponseEntity.status(404).body("Producto no encontrado [X_X]");
        }

        return ResponseEntity.ok(producto);
    }

    // -- GET: BUSCAR POR TITULO -- //

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<?> buscarPorTitulo(@PathVariable String titulo) {
        
        List<ProductoResumenDTO> producto = Service.buscarPorTitulo(titulo);

        if (producto.isEmpty()) {

            return ResponseEntity.status(404).body("Producto no encontrado [X_X]");
        }

        return ResponseEntity.ok(producto);
    }

    // -- GET: BUSCAR POR GENERO -- //

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> buscarPorGenero(@PathVariable String genero) {
        
        List<ProductoResumenDTO> producto = Service.buscarPorGenero(genero);

        if (producto.isEmpty()) {

            return ResponseEntity.status(404).body("Producto no encontrado [X_X]");
        }

        return ResponseEntity.ok(producto);


    }

    // -- GET: BUSCAR POR EDITORIAL -- //

    @GetMapping("/editorial/{editorial}")
    public ResponseEntity<?> buscarPorEditorial(@PathVariable String editorial) {
        
        List<ProductoResumenDTO> producto = Service.buscarPorEditorial(editorial);

        if (producto.isEmpty()) {

            return ResponseEntity.status(404).body("Producto no encontrado [X_X]");
        }

        return ResponseEntity.ok(producto);

    }

    // -- GET: BUSCAR POR CATEGORIA -- //

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoria) {

        List<ProductoResumenDTO> producto = Service.buscarPorCategoria(categoria);

        if (producto.isEmpty()) {

            return ResponseEntity.status(404).body("Producto no encontrado [X_X]");
        }

        return ResponseEntity.ok(producto);
    }

    // -- GET: BUSCAR POR AÑO -- //

    @GetMapping("/anio/{anio}")
    public ResponseEntity<?> buscarPorAnio(@PathVariable Integer anio) {
        
        List<ProductoResumenDTO> producto = Service.buscarPorAnio(anio);

        if (producto.isEmpty()) {

            return ResponseEntity.status(404).body("Producto no encontrado [X_X]");
        }

        return ResponseEntity.ok(producto);

    }


    // -- GET: BUSCAR POR AÑO -- //

    @GetMapping("/autor/{autor}")
    public ResponseEntity<?> buscarPorAutor(@PathVariable String autor) {
        
        List<ProductoResumenDTO> producto = Service.buscarPorAutor(autor);

        if (producto.isEmpty()) {

            return ResponseEntity.status(404).body("Producto no encontrado [X_X]");
        }

        return ResponseEntity.ok(producto);

    }

    // -- POST: CREAR PRODUCTO -- //
    
    @PostMapping("/crear")
    public ResponseEntity<?> Crear(@Valid @RequestBody Catalogo producto) {

    String mensaje = Service.guardar(producto);

    if (mensaje.toLowerCase().contains("ya existe")) {

        return ResponseEntity.badRequest().body(mensaje);
    }


    return ResponseEntity.status(201).body(mensaje);
}
    
    // -- DELETE: ELIMINAR PRODUCTO -- //

    @DeleteMapping("/eliminarxid/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Integer id) {
        
        String mensaje = Service.eliminar(id);

        if (mensaje.toLowerCase().contains("no encontrado") ||
            mensaje.toLowerCase().contains("no encontrada")) {

            return ResponseEntity.status(404).body(mensaje);
        }
        
        return ResponseEntity.ok(mensaje);

    }

    // -- PUT: ACTUALIZAR PRODUCTO -- //

    @PutMapping("/actualizar")
    public ResponseEntity<?> Actualizar(@Valid @RequestBody Catalogo catalogo) {

        String mensaje = Service.actualizar(catalogo);

        if (mensaje.toLowerCase().contains("no encontrado") ||
            mensaje.toLowerCase().contains("no encontrada")) {
                return ResponseEntity.status(404).body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
        
    }
}
