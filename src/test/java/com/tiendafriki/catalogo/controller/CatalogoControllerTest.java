package com.tiendafriki.catalogo.controller;

// Importaciones correspondientes de catalogo:
import com.tiendafriki.catalogo.dto.ProductoRequestDTO;
import com.tiendafriki.catalogo.dto.ProductoResponseDTO;
import com.tiendafriki.catalogo.service.CatalogoService;

// Importación de ObjectMapper:: Servirá para mapear los objetos de catalogo
// para los DTO de Request y Response
import com.fasterxml.jackson.databind.ObjectMapper;

// Importación de Autowired
import org.springframework.beans.factory.annotation.Autowired;
// Importación de Lista
import java.util.List;

// Importación de Jupiter para testing
import org.junit.jupiter.api.Test;

// Importaciones propias de Mociot y testing:
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// Importaciones de argumentos

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Impportaciones de peticiones HTTP y status

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogoController.class) // Levanta solamente el Controller.
class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc; //Sirve para simular peticiones HTTP

    @MockitoBean //Crea un servicio falso.No se conecta a BD.No ejecuta lógica real.
    private CatalogoService service;

    // Cree un ObjectMapper para mapear los productos de catalogo cuando los necesitemos

   private final ObjectMapper objectMapper = new ObjectMapper();

   // Esta función creará un RequestDTO de producto
   //(es decir, el formato del producto que ingresa el usuario
   // en las solcitudes de crear y actualizar.
   // Basicamente simular lo que se ingresa en el JSON al crear y actualizar)
   // para poder reutilizarlo en los endpoints POST y PUT que lo utilizan

   // Aqui reemplazenlo por el DTO o Clase que usen para mostrar en los POST y PUT

    private ProductoRequestDTO crearRequestDTO() {

        return new ProductoRequestDTO(
                null,
                "One Piece",
                "Aventura",
                1997,
                "Eiichiro Oda",
                "Shueisha",
                "Manga",
                180,
                10000
        );
    }

    // Esta función creará un ResponseDTO de producto
   // (es decir, el formato del producto cuando que se muestra 
   // en las solicitudes de listar y buscar)
   // para poder reutilizarlo en los endpoints GET que lo utilizan

   // Aqui reemplazenlo por el DTO o Clase que usen para mostrar en los GET

    private ProductoResponseDTO crearResponseDTO() {

        return new ProductoResponseDTO(
                1,
                "One Piece",
                "Aventura",
                1997,
                "Eiichiro Oda",
                "Shueisha",
                "Manga",
                180,
                10000
        );
    }

    // Test de listar:

    @Test
    void listarProductos() throws Exception {

        when(service.listar()) // Cuando el service ejecute el metodo listar
                .thenReturn(List.of(crearResponseDTO())); // Entonces debe retornar una lista de productos (aqui uso mi metodo para crear el ResponseDTO)

        mockMvc.perform(get("/catalogo/listar")) // Obtenemos la URL para simular la solicitud
                .andExpect(status().isOk()); //verifica si el endpoint respondió de forma exitosa con codigo 200
    }

    @Test
    void buscarPorId() throws Exception {

        when(service.buscarPorId(1))
                .thenReturn(crearResponseDTO());

        mockMvc.perform(get("/catalogo/buscarxid/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorTitulo() throws Exception {

        when(service.buscarPorTitulo("One Piece"))
                .thenReturn(List.of(crearResponseDTO()));

        mockMvc.perform(get("/catalogo/titulo/One Piece"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorGenero() throws Exception {

        when(service.buscarPorGenero("Aventura"))
                .thenReturn(List.of(crearResponseDTO()));

        mockMvc.perform(get("/catalogo/genero/Aventura"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorEditorial() throws Exception {

        when(service.buscarPorEditorial("Shueisha"))
                .thenReturn(List.of(crearResponseDTO()));

        mockMvc.perform(get("/catalogo/editorial/Shueisha"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorCategoria() throws Exception {

        when(service.buscarPorCategoria("Manga"))
                .thenReturn(List.of(crearResponseDTO()));

        mockMvc.perform(get("/catalogo/categoria/Manga"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorAnio() throws Exception {

        when(service.buscarPorAnio(1997))
                .thenReturn(List.of(crearResponseDTO()));

        mockMvc.perform(get("/catalogo/anio/1997"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorAutor() throws Exception {

        when(service.buscarPorAutor("Eiichiro Oda"))
                .thenReturn(List.of(crearResponseDTO()));

        mockMvc.perform(get("/catalogo/autor/Eiichiro Oda"))
                .andExpect(status().isOk());
    }

    // Test de Guardar: 

    @Test
    void crearProducto() throws Exception {

        // Simulamos lo que haria el service al guardar un producto
        when(service.guardar(any())) // Cuando el service ejecute el metodo guardar
                .thenReturn("[+] El Producto se agregó correctamente al catálogo ... "); // Entonces debe devolver este mensaje (aqui hay que poner que se supone devuelve el service)

        mockMvc.perform(post("/catalogo/crear") // Obtenemos la URL para simular la solicitud al crear
                        .contentType(MediaType.APPLICATION_JSON) // Indicamos que el contenido enviado es una petición JSON
                        .content(objectMapper.writeValueAsString(crearRequestDTO()))) // Simula el cuerpo del request, en mi caso use un objectMapper para reutilizar mi EequestDTO de producto
                .andExpect(status().isCreated());  // verifica si el endpoint creó el objeto de forma exitosa con codigo 201
    }

    @Test
    void actualizarProducto() throws Exception {

        when(service.actualizar(any(), any()))
                .thenReturn("[+] El Producto Del Catalogo Fue Actualizado ... ");

        mockMvc.perform(put("/catalogo/actualizarxid/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearRequestDTO())))
                .andExpect(status().isOk());
    }

    @Test
    void descontarStock() throws Exception {

        when(service.descontarStock(1, 2))
                .thenReturn("[+] Stock actualizado correctamente");

        mockMvc.perform(put("/catalogo/descontarstock/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarProducto() throws Exception {

        when(service.eliminar(1))
                .thenReturn("[+] Producto Eliminado Del Catalogo ... ");

        mockMvc.perform(delete("/catalogo/eliminarxid/1"))
                .andExpect(status().isOk());
    }
}
