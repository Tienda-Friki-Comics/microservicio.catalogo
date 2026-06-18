package com.tiendafriki.catalogo.controller;

import com.tiendafriki.catalogo.model.Editorial;
import com.tiendafriki.catalogo.service.EditorialService;

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

@WebMvcTest(EditorialController.class)
public class EditorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private EditorialService service;

   private final ObjectMapper objectMapper = new ObjectMapper();

    private Editorial crearEditorialDTO() {

        return new Editorial(
                null,
                "manga"
        );
    }

    @Test
    void listarEditoriales() throws Exception {

        when(service.listar()) 
                .thenReturn(List.of(crearEditorialDTO()));

        mockMvc.perform(get("/editorial/listar")) 
                .andExpect(status().isOk()); 
    }

    @Test
    void buscarPorId() throws Exception {

        when(service.buscarPorId(1))
                .thenReturn(crearEditorialDTO());

        mockMvc.perform(get("/editorial/buscarxid/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorNombre() throws Exception {

        when(service.buscarPorNombre("manga"))
                .thenReturn(crearEditorialDTO());

        mockMvc.perform(get("/editorial/nombre/manga"))
                .andExpect(status().isOk());
    }

    @Test
    void crearUnaEditorial() throws Exception {

        when(service.guardar(any()))
                .thenReturn("[+] La Editorial Se Ha Guardado Correctamente ...");

        mockMvc.perform(post("/editorial/crear") 
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearEditorialDTO()))) 
                .andExpect(status().isCreated()); 
    }

    @Test
    void actualizarEditorial() throws Exception {

        when(service.actualizar(any(), any()))
                .thenReturn("[+] La Editorial Fue Actualizada Correctamente ... ");

        mockMvc.perform(put("/editorial/actualizarxid/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearEditorialDTO())))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarEditorial() throws Exception {

        when(service.eliminar(1))
                .thenReturn("[+] La Editorial Fue Eliminada Correctamente ... ");

        mockMvc.perform(delete("/editorial/eliminarxid/1"))
                .andExpect(status().isOk());
    }

}
