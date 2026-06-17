package com.tiendafriki.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tiendafriki.catalogo.model.Catalogo;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogoRepo extends JpaRepository<Catalogo, Integer> {

    Optional<Catalogo> findByTituloIgnoreCase(String titulo);

    List<Catalogo> findByTituloContainingIgnoreCase(String titulo);

    List<Catalogo> findByGeneroIgnoreCase(String genero);

    List<Catalogo> findByAutorIgnoreCase(String autor);

    List<Catalogo> findByAnio(Integer anio);

    List<Catalogo> findByEditorialNombreIgnoreCase(String nombreEditorial);

    List<Catalogo> findByCategoriaNombreIgnoreCase(String nombreCategoria);

    boolean existsByEditorialId(Integer id);

    boolean existsByCategoriaId(Integer id);
}
