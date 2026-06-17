package com.tiendafriki.catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tiendafriki.catalogo.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.tiendafriki.catalogo.dto.ProductoRequestDTO;
import com.tiendafriki.catalogo.dto.ProductoResponseDTO;
import jakarta.validation.Valid;
import java.util.*;

// REVISADO OK
// Nada que corregir

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService Service;


    // GET: listar todo el catalogo
    @Operation(
        summary = "Listar catalogo",
        description = "Obtiene una lista con todos los productos registrados en el catalogo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public List<ProductoResponseDTO> listar() {

        return Service.listar();
    }

    // GET: buscar por id
    @Operation(
        summary = "Buscar producto por id",
        description = "Obtiene un producto del catalogo por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarxid/{id}")
    public ProductoResponseDTO buscarPorId(@PathVariable Integer id) {

        return Service.buscarPorId(id);
    }

    // GET: buscar por titulo
    @Operation(
        summary = "Buscar producto por titulo",
        description = "Obtiene una lista de productos dle catalogo que coincidan con el titulo ingresado por el usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/titulo/{titulo}")
    public List<ProductoResponseDTO> buscarPorTitulo(@PathVariable String titulo) {
        
        return Service.buscarPorTitulo(titulo);

    }

    // GET: buscar por genero
    @Operation(
        summary = "Buscar productos por genero",
        description = "Obtiene una lista de productos del mismo genero"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/genero/{genero}")
    public List<ProductoResponseDTO> buscarPorGenero(@PathVariable String genero) {
        
        return Service.buscarPorGenero(genero);

    }

    // GET: buscar por editorial
    @Operation(
        summary = "Buscar productos por editorial",
        description = "Obtiene una lista de productos de la misma editorial"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/editorial/{editorial}")
    public List<ProductoResponseDTO> buscarPorEditorial(@PathVariable String editorial) {
        
        return Service.buscarPorEditorial(editorial);

    }

    // GET: buscar por categoria
    @Operation(
        summary = "Buscar productos por categoria",
        description = "Obtiene una lista de productos de la misma categoria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/categoria/{categoria}")
    public List<ProductoResponseDTO> buscarPorCategoria(@PathVariable String categoria) {

        return Service.buscarPorCategoria(categoria);

    }

    // GET: buscar por año
    @Operation(
        summary = "Buscar productos por año",
        description = "Obtiene una lista de productos del mismo año de publicación"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/anio/{anio}")
    public List<ProductoResponseDTO> buscarPorAnio(@PathVariable Integer anio) {
        
        return Service.buscarPorAnio(anio);

    }

    // GET: buscar por autor
    @Operation(
        summary = "Buscar productos por autor",
        description = "Obtiene una lista de productos de un mismo autor"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/autor/{autor}")
    public List<ProductoResponseDTO> buscarPorAutor(@PathVariable String autor) {
        
        return Service.buscarPorAutor(autor);

    }

    // POST: Crear producto
    @Operation(
        summary = "Registrar un producto",
        description = "Permite registrar un nuevo producto en el catalogo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Categoría o Editorial no encontradas o inexistentes"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    
    @PostMapping("/crear")
    public ResponseEntity<String> Crear(@Valid @RequestBody ProductoRequestDTO producto) {

        String mensaje = Service.guardar(producto);

        return ResponseEntity.status(201).body(mensaje);
    }

    // PUT: Actualizar producto
    @Operation(
        summary = "Actualizar un producto",
        description = "Permite actualizar un producto existente en el catalogo por id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizarxid/{id}")
    public ResponseEntity<String> Actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoRequestDTO catalogo) {

        String mensaje = Service.actualizar(id, catalogo);

        return ResponseEntity.ok(mensaje);
        
    }

    // PUT: Descontar Stock de producto
    @Operation(
        summary = "Descontar stock de un producto",
        description = "Permite descontar automaticamente el stock de un producto cuando se realiza una compra en el microservicio Pedidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock descontado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/descontarstock/{productoId}/{cantidad}")
    public ResponseEntity<String> descontarStock(@PathVariable Integer productoId, @PathVariable Integer cantidad) {

        String mensaje = Service.descontarStock(productoId, cantidad);

        return ResponseEntity.ok(mensaje);
    }

    // DELETE: Eliminar producto
    @Operation(
        summary = "Eliminar producto",
        description = "Permite eliminar un producto existente en el catalogo por medio de su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de lógica de negocio")  ,
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminarxid/{id}")
    public ResponseEntity<String> Eliminar(@PathVariable Integer id) {
        
        String mensaje = Service.eliminar(id);
        
        return ResponseEntity.ok(mensaje);

    }
}
