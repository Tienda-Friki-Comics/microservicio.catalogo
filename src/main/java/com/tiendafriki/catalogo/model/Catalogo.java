// === CATALOGO === //

package com.tiendafriki.catalogo.model;

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

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private String autor;

    // Relación con editorial

    @ManyToOne
    @JoinColumn(name = "ID_Editorial", nullable = false)
    private Editorial editorial;

    // Relación con categoria

    @ManyToOne
    @JoinColumn(name = "ID_Categoria", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer precio;
}