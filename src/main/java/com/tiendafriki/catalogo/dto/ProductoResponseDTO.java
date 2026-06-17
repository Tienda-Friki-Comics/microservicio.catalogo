// -- PRODUCTO RESUMIDO DTO -- //

package com.tiendafriki.catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoResponseDTO {

    private Integer id;
    private String titulo;
    private String genero;
    private Integer anio;
    private String autor;

    private String editorial; 
    private String categoria; 

    private Integer stock;
    private Integer precio;

}
