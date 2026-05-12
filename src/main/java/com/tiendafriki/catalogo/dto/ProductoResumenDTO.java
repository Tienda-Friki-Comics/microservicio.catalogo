// -- PRODUCTO RESUMIDO DTO -- //

package com.tiendafriki.catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// Este DTO esta diseñado para resumir la información de los productos expuestos del catálogo

// RECOMENDACIÓN: Se recomienda usar este DTO para los endpoints que soliciten la información de los productos
// y también para los microservicios externos que necesiten consultar los productos de catálogo

@Data
@AllArgsConstructor
public class ProductoResumenDTO {

    private Integer id;
    private String titulo;
    private String genero;
    private Integer anio;
    private String autor;

    private String editorial; // El objeto editorial es convertido en String por el método convertirADTO del Service.
    private String categoria; // El objeto categoría es convertido en String por el método convertirADTO del Service.

    private Integer stock;
    private Integer precio;

}
