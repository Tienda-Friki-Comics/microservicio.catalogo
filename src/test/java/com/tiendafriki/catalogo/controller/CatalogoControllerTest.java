package com.tiendafriki.catalogo.controller;

import com.tiendafriki.catalogo.dto.ProductoRequestDTO;
import com.tiendafriki.catalogo.dto.ProductoResponseDTO;
import com.tiendafriki.catalogo.service.CatalogoService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogoController.class)
class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatalogoService service;

   private final ObjectMapper objectMapper = new ObjectMapper();

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

    @Test
    void listarProductos() throws Exception {

        when(service.listar()) 
                .thenReturn(List.of(crearResponseDTO())); 

        mockMvc.perform(get("/catalogo/listar")) 
                .andExpect(status().isOk());
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

    @Test
    void crearProducto() throws Exception {

        when(service.guardar(any())) 
                .thenReturn("[+] El Producto se agregó correctamente al catálogo ... ");

        mockMvc.perform(post("/catalogo/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearRequestDTO())))
                .andExpect(status().isCreated());
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
