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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialService Service;

    // GET: listar todas las editoriales
    @Operation(
        summary = "Listar editoriales",
        description = "Obtiene una lista con todos las editoriales registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public List<Editorial> listar() {

        return Service.listar();

    }

    // GET: buscar por id
    @Operation(
        summary = "Buscar editorial por id",
        description = "Obtiene una editorial por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio"),
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarxid/{id}")
    public Editorial buscarPorId(@PathVariable Integer id) {

        return Service.buscarPorId(id);

    }

    // GET: buscar por nombre
    @Operation(
        summary = "Buscar editorial por nombre",
        description = "Obtiene una editorial por su nombre"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio"),
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/nombre/{nombre}")
    public Editorial buscarPorNombre(@PathVariable String nombre) {

        return Service.buscarPorNombre(nombre);

    }

    // POST: Crear editorial
    @Operation(
        summary = "Registrar una editorial",
        description = "Permite registrar una nueva editorial para los productos del catalogo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Editorial creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    
    @PostMapping("/crear")
    public ResponseEntity<String> Crear(@Valid @RequestBody Editorial editorial) {
        String mensaje = Service.guardar(editorial);
        return ResponseEntity.status(201).body(mensaje);
    }
    
    // DELETE: Eliminar editorial
    @Operation(
        summary = "Eliminar editorial",
        description = "Permite eliminar una editorial existente por medio de su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminarxid/{id}")
    public ResponseEntity<String> Eliminar(@PathVariable Integer id) {
        
        String mensaje = Service.eliminar(id);
        return ResponseEntity.ok(mensaje);

    }

    // PUT: Actualizar editorial
    @Operation(
        summary = "Actualizar una editorial",
        description = "Permite actualizar una editorial existente por medio de su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizarxid/{id}")
    public ResponseEntity<String> Actualizar(@PathVariable Integer id, @Valid @RequestBody Editorial editorial) {

        String mensaje = Service.actualizar(id, editorial);
        return ResponseEntity.ok(mensaje);
        
    }

}
