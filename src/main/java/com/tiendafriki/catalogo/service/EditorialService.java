package com.tiendafriki.catalogo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendafriki.catalogo.model.Editorial;
import com.tiendafriki.catalogo.repository.CatalogoRepo;
import com.tiendafriki.catalogo.repository.EditorialRepo;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepo repository;

    @Autowired
    private CatalogoRepo catalogoRepo;

    // -- LISTAR TODAS LAS CATEGORIAS -- //
    public List<Editorial> listar() {

        return repository.findAll();
    }

    // -- BUSCAR POR ID -- //

    public Editorial buscarPorId(Integer id) {

        return repository.findById(id)
                        .orElseThrow(() ->
                        new NoSuchElementException
                        ( "[ERROR] Editorial No Encontrada [X_X] ..."));
    }

    // -- BUSCAR POR NOMBRE EDITORIAL -- //

    public Editorial buscarPorNombre(String nombre) {

        return repository.findByNombreIgnoreCase(nombre)
                        .orElseThrow(() ->
                        new NoSuchElementException
                        ( "[ERROR] Editorial No Encontrada [X_X] ..."));
    }

    // -- CREAR EDITORIAL -- //

    public String guardar(Editorial editorial){

        // Validar que NO exista: //

        // buscamos por el nombre exacto en el repository y lo guardamos en existente

        Optional<Editorial> existente = repository.findByNombreIgnoreCase(editorial.getNombre());

        // isPresent(): Comrpueba si el contenedor Optional no tiene nulo
        // Si la editorial existe y esta presente en la BD

        if(existente.isPresent()){

             throw new IllegalArgumentException(
                    "[ERROR] La editorial ya existe [X_X] ...");

        }

        // Si no existe, la guardamos:

        repository.save(editorial);

        return "[+] La Editorial Se Ha Guardado Correctamente ...";

    }


    // -- MÉTODO ACTUALIZAR EDITORIAL (PUT) -- //

    public String actualizar(Integer id, Editorial editorial) {

        // Buscamos Editorial por id:

        Optional<Editorial> editOpt = repository.findById(id);

        // Validamos existencia

        // isEmpty(): Comprueba si el contenedor Optional esta vacio

        if(editOpt.isEmpty()){

            // En caso de no existir, devolvemos mensaje de error

            throw new NoSuchElementException(
                    "[ERROR] Editorial No Encontrada [X_X] ...");

        }

        // En caso contrario, guardamos la editorial actualizada

        repository.save(editorial);

        return "[+] La Editorial Fue Actualizada Correctamente ... ";
    }

    // -- MÉTODO ELIMINAR EDITORIAL (DELETE) -- //

    public String eliminar(Integer id) {

        // Buscamos la editorial por id

        Optional<Editorial> editOpt = repository.findById(id);

        // Validamos existencia

        if(editOpt.isEmpty()){

            // En caso de no existir, devolvemos mensaje de error

            throw new NoSuchElementException(
                    "[ERROR] Editorial No Encontrada [X_X] ...");

        }

        // === VALIDAR SI LA EDITORIAL ESTA SIENDO USADA === //

        // Si existe algun producto asociado a esta editorial,
        // NO permitimos eliminarla

        if (catalogoRepo.existsByEditorialId(id)) {

            throw new IllegalArgumentException(
                    "[ERROR] No se puede eliminar la editorial porque tiene productos asociados [X_X] ..."
            );
        }

        // En caso contrario, eliminamos la editorial

        repository.deleteById(id);

        return "[+] La Editorial Fue Eliminada Correctamente ... ";

    }

}
