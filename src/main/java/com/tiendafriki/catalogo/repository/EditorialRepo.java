// --- EditorialRepository --- //

package com.tiendafriki.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tiendafriki.catalogo.model.Editorial;

import java.util.List;
import java.util.Optional;


@Repository
public interface EditorialRepo extends JpaRepository<Editorial, Integer> {

    // findAll() -> Listar todo
    // findById(id) -> Buscar por Id
    // save(peliula) -> Guardar o actualizar
    // deleteById(id) -> eleminar por ID

    // -- BUSCAR POR NOMBRE (EXACTO) -- //
    Optional<Editorial> findByNombreIgnoreCase(String nombre);

    // -- BUSCAR POR NOMBRE (coincidencias) -- //
    
    // Buscará cualquier categoria que contenga en su nombre una coincidencia
    //con lo ingresado por el usuario, más flexible

    List<Editorial> findByNombreContainingIgnoreCase(String nombre);

}
