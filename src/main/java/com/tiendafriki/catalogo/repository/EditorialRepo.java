// --- EditorialRepository --- //

package com.tiendafriki.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tiendafriki.catalogo.model.Editorial;

import java.util.List;
import java.util.Optional;


@Repository
public interface EditorialRepo extends JpaRepository<Editorial, Integer> {

    Optional<Editorial> findByNombreIgnoreCase(String nombre);

    List<Editorial> findByNombreContainingIgnoreCase(String nombre);

}
