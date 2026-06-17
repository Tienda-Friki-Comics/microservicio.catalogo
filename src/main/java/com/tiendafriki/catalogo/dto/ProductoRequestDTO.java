package com.tiendafriki.catalogo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDTO {

    private Integer id;

    @NotBlank(message = "[ERROR] El Titulo No Puede Estar En Blanco  [X_X] ... ")
    private String titulo;

    @NotBlank(message = "[ERROR] El Genero No Puede Quedar Vacio  [X_X] ... ")
    private String genero;

    @Min(value = 1500, message = "[+] El Año Debe Ser Mayor A 1.500 ... ")
    @NotNull(message = "[ERROR] El Año No Puede Quedar Nulo  [X_X] ... ")
    private Integer anio;

    @NotBlank(message = "[ERROR] El Autor No Puede Quedar Vacio  [X_X] ... ")
    private String autor;

    @NotBlank(message = "[ERROR] La Editorial No Puede Quedar Vacia  [X_X] ... ")
    private String editorial;

    @NotBlank(message = "[ERROR] La Categoria No Puede Quedar Vacia  [X_X] ... ")
    private String categoria;

    @Min(value = 1, message = "[ERROR] El Stock Debe Ser Mayor A 1  [X_X] ... ")
    @NotNull(message = "[ERROR] El Stock No Puede Quedar Nulo  [X_X] ... ")
    private Integer stock;

    @Positive
    @NotNull(message = "[ERROR] El Precio No Debe Ser Nulo  [X_X] ... ")
    private Integer precio;

}
