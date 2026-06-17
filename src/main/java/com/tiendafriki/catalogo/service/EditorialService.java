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

    public List<Editorial> listar() {

        return repository.findAll();
    }

    public Editorial buscarPorId(Integer id) {

        return repository.findById(id)
                        .orElseThrow(() ->
                        new NoSuchElementException
                        ( "[ERROR] Editorial No Encontrada [X_X] ..."));
    }

    public Editorial buscarPorNombre(String nombre) {

        return repository.findByNombreIgnoreCase(nombre)
                        .orElseThrow(() ->
                        new NoSuchElementException
                        ( "[ERROR] Editorial No Encontrada [X_X] ..."));
    }

    public String guardar(Editorial editorial){

        Optional<Editorial> existente = repository.findByNombreIgnoreCase(editorial.getNombre());

        if(existente.isPresent()){

             throw new IllegalArgumentException(
                    "[ERROR] La editorial ya existe [X_X] ...");

        }

        repository.save(editorial);

        return "[+] La Editorial Se Ha Guardado Correctamente ...";

    }

    public String actualizar(Integer id, Editorial editorial) {

        Optional<Editorial> editOpt = repository.findById(id);

        if(editOpt.isEmpty()){

            throw new NoSuchElementException(
                    "[ERROR] Editorial No Encontrada [X_X] ...");

        }

        repository.save(editorial);

        return "[+] La Editorial Fue Actualizada Correctamente ... ";
    }

    public String eliminar(Integer id) {

        Optional<Editorial> editOpt = repository.findById(id);

        if(editOpt.isEmpty()){

            throw new NoSuchElementException(
                    "[ERROR] Editorial No Encontrada [X_X] ...");

        }

        if (catalogoRepo.existsByEditorialId(id)) {

            throw new IllegalArgumentException(
                    "[ERROR] No se puede eliminar la editorial porque tiene productos asociados [X_X] ..."
            );
        }

        repository.deleteById(id);

        return "[+] La Editorial Fue Eliminada Correctamente ... ";

    }

}
