package com.tiendafriki.catalogo.controller;

import com.tiendafriki.catalogo.model.Categoria;
import com.tiendafriki.catalogo.service.CategoriaService;

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

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private CategoriaService service;

   private final ObjectMapper objectMapper = new ObjectMapper();

    private Categoria crearCategoria() {

        return new Categoria(
                null,
                "Shueisha"
        );
    }


    @Test
    void listarCategorias() throws Exception {

        when(service.listar()) 
                .thenReturn(List.of(crearCategoria()));

        mockMvc.perform(get("/categoria/listar")) 
                .andExpect(status().isOk()); 
    }

    @Test
    void buscarPorId() throws Exception {

        when(service.buscarPorId(1))
                .thenReturn(crearCategoria());

        mockMvc.perform(get("/categoria/buscarxid/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorNombre() throws Exception {

        when(service.buscarPorNombre("Shueisha"))
                .thenReturn(crearCategoria());

        mockMvc.perform(get("/categoria/nombre/Shueisha"))
                .andExpect(status().isOk());
    }

    @Test
    void crearUnaCategoria() throws Exception {

        when(service.guardar(any()))
                .thenReturn("[+] La Categoria Se Ha Guardado Correctamente ...");

        mockMvc.perform(post("/categoria/crear") 
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearCategoria()))) 
                .andExpect(status().isCreated()); 
    }

    @Test
    void actualizarCategoria() throws Exception {

        when(service.actualizar(any(), any()))
                .thenReturn("[+] La Categoria Fue Actualizada Correctamente ... ");

        mockMvc.perform(put("/categoria/actualizarxid/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearCategoria())))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarCategoria() throws Exception {

        when(service.eliminar(1))
                .thenReturn("[+] La Categoria Fue Eliminada Correctamente ... ");

        mockMvc.perform(delete("/categoria/eliminarxid/1"))
                .andExpect(status().isOk());
    }

}
