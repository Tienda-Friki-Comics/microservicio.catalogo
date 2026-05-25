package com.tiendafriki.catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tiendafriki.catalogo.service.*;
import com.tiendafriki.catalogo.dto.ProductoRequestDTO;
import com.tiendafriki.catalogo.dto.ProductoResponseDTO;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService Service;

    // -- GET: LISTAR TODO -- //

    @GetMapping("/listar")
    public List<ProductoResponseDTO> listar() {

        //List<ProductoResumenDTO> catalogo = Service.listarDTO();

        return Service.listar();
    }

    // -- GET: BUSCAR POR ID -- //

    @GetMapping("/buscarxid/{id}")
    public ProductoResponseDTO buscarPorId(@PathVariable Integer id) {

        return Service.buscarPorId(id);
    }

    // -- GET: BUSCAR POR TITULO -- //

    @GetMapping("/titulo/{titulo}")
    public List<ProductoResponseDTO> buscarPorTitulo(@PathVariable String titulo) {
        
        return Service.buscarPorTitulo(titulo);

    }

    // -- GET: BUSCAR POR GENERO -- //

    @GetMapping("/genero/{genero}")
    public List<ProductoResponseDTO> buscarPorGenero(@PathVariable String genero) {
        
        return Service.buscarPorGenero(genero);

    }

    // -- GET: BUSCAR POR EDITORIAL -- //

    @GetMapping("/editorial/{editorial}")
    public List<ProductoResponseDTO> buscarPorEditorial(@PathVariable String editorial) {
        
        return Service.buscarPorEditorial(editorial);

    }

    // -- GET: BUSCAR POR CATEGORIA -- //

    @GetMapping("/categoria/{categoria}")
    public List<ProductoResponseDTO> buscarPorCategoria(@PathVariable String categoria) {

        return Service.buscarPorCategoria(categoria);

    }

    // -- GET: BUSCAR POR AÑO -- //

    @GetMapping("/anio/{anio}")
    public List<ProductoResponseDTO> buscarPorAnio(@PathVariable Integer anio) {
        
        return Service.buscarPorAnio(anio);

    }


    // -- GET: BUSCAR POR AUTOR -- //

    @GetMapping("/autor/{autor}")
    public List<ProductoResponseDTO> buscarPorAutor(@PathVariable String autor) {
        
        return Service.buscarPorAutor(autor);

    }

    // -- POST: CREAR PRODUCTO -- //
    
    @PostMapping("/crear")
    public ResponseEntity<String> Crear(@Valid @RequestBody ProductoRequestDTO producto) {

        String mensaje = Service.guardar(producto);

        return ResponseEntity.status(201).body(mensaje);
    }
    

    // -- PUT: ACTUALIZAR PRODUCTO -- //

    @PutMapping("/actualizarxid/{id}")
    public ResponseEntity<String> Actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoRequestDTO catalogo) {

        String mensaje = Service.actualizar(id, catalogo);

        return ResponseEntity.ok(mensaje);
        
    }

    // === PUT: ACTUALIZAR STOCK === //

    @PutMapping("/descontarstock/{productoId}/{cantidad}")
    public ResponseEntity<String> descontarStock(@PathVariable Integer productoId, @PathVariable Integer cantidad) {

        String mensaje = Service.descontarStock(productoId, cantidad);

        return ResponseEntity.ok(mensaje);
    }

    // -- DELETE: ELIMINAR PRODUCTO -- //

    @DeleteMapping("/eliminarxid/{id}")
    public ResponseEntity<String> Eliminar(@PathVariable Integer id) {
        
        String mensaje = Service.eliminar(id);
        
        return ResponseEntity.ok(mensaje);

    }
}
