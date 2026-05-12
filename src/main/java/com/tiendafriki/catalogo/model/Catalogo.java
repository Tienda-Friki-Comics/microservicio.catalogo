// --- CATALOGO --- //

package com.tiendafriki.catalogo.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Catalogo")
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "[+] El Titulo No Puede Estar En Blanco ... ")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank(message = "[+] El Genero No Puede Quedar Vacio ... ")
    private String genero;

    @Min(value = 1500, message = "[+] El Año Debe Ser Mayor A 1.500 ... ")
    @NotNull(message = "[+] El Año No Puede Quedar Nulo ... ")
    private Integer anio;

    @NotBlank(message = "[+] El Autor No Puede Quedar Vacio ... ")
    private String autor;

    // Relación con editorial

    @ManyToOne // Una editorial puede aparecer en muchos productos
    @JoinColumn(name = "ID_Editorial", nullable = false) // FK con editorial
    @NotNull(message = "[+] La Editorial No Puede Quedar Nula ... ")
    private Editorial editorial;

    // Relación con categoria
    @ManyToOne // Una categoria puede aparecer en muchos productos
    @JoinColumn(name = "ID_Categoria", nullable = false) // FK con categoria
    @NotNull(message = "[+] La Categoria No Puede Quedar Nula ... ")
    private Categoria categoria;

    @Min(value = 1, message = "[+] El Numero Del Stock Debe Ser Mayor A 1 ... ")
    @NotNull(message = "[+] El Stock No Puede Quedar Nulo ... ")
    private Integer stock;

    @Min(value = 9990, message = "[+] El Precio Debe Ser Mayor A 9990 ... ")
    @NotNull(message = "[+] El Precio No Debe Ser Nulo ... ")
    private Integer precio;

}