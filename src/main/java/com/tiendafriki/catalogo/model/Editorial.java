// --- EDITORIAL --- //

package com.tiendafriki.catalogo.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Editorial")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Editorial")
    private Integer id;

    @NotBlank(message = "[ERROR] El nombre No Puede Estar En Blanco  [X_X] ... ")
    @Column(nullable = false, length = 100)
    private String nombre;

}
