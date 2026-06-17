// --- CategoriaRepository --- //

package com.tiendafriki.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tiendafriki.catalogo.model.Categoria;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepo  extends JpaRepository<Categoria, Integer>{

    Optional<Categoria> findByNombreIgnoreCase(String nombre);

    List<Categoria> findByNombreContainingIgnoreCase(String nombre);

}
