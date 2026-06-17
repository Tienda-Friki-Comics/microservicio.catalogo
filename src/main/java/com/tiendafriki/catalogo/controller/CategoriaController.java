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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService Service;

    // GET: listar todas las categorias
    @Operation(
        summary = "Listar categorias",
        description = "Obtiene una lista con todos las categorias de productos registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public  List<Categoria> listar() {

        return Service.listar();

    }

    // GET: buscar por id
    @Operation(
        summary = "Buscar categoria por id",
        description = "Obtiene una categoria por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarxid/{id}")
    public Categoria buscarPorId(@PathVariable Integer id) {

        return Service.buscarPorId(id);

    }

    // GET: buscar por nombre
    @Operation(
        summary = "Buscar categoria por nombre",
        description = "Obtiene una categoria por su nombre"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/nombre/{nombre}")
    public Categoria buscarPorNombre(@PathVariable String nombre) {

        return Service.buscarPorNombre(nombre);

    }

    // POST: Crear categoria
    @Operation(
        summary = "Registrar una categoria",
        description = "Permite registrar una nueva categoria para los productos del catalogo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    
    @PostMapping("/crear")
    public ResponseEntity<String> Crear(@Valid @RequestBody Categoria categoria) {

        String mensaje = Service.guardar(categoria);

        return ResponseEntity.status(201).body(mensaje);
    }

    // DELETE: Eliminar categoria
    @Operation(
        summary = "Eliminar categoria",
        description = "Permite eliminar una categoria existente por medio de su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminarxid/{id}")
    public ResponseEntity<String> Eliminar(@PathVariable Integer id) {
        
        String mensaje = Service.eliminar(id);
        
        return ResponseEntity.ok(mensaje);

    }

    // PUT: Actualizar categoria
    @Operation(
        summary = "Actualizar una categoria",
        description = "Permite actualizar una categoria existente por medio de su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizarxid/{id}")
    public ResponseEntity<String> Actualizar(@PathVariable Integer id, @Valid @RequestBody Categoria categoria) {

        String mensaje = Service.actualizar(id, categoria);

        return ResponseEntity.ok(mensaje);
        
    }

}