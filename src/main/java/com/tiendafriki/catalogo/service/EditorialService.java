package com.tiendafriki.catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendafriki.catalogo.model.Editorial;
import com.tiendafriki.catalogo.repository.EditorialRepo;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepo repository;

    // -- LISTAR TODAS LAS CATEGORIAS -- //
    public List<Editorial> listar() {
        return repository.findAll();
    }

    // -- BUSCAR POR ID -- //

    public Optional<Editorial> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    // -- BUSCAR POR NOMBRE EDITORIAL -- //

    public Optional<Editorial> buscarPorNombre(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }

    // -- CREAR EDITORIAL -- //

    public String guardar(Editorial editorial){

        // Validar que NO exista: //

        // buscamos por el nombre exacto en el repository y lo guardamos en existente

        Optional<Editorial> existente = repository.findByNombreIgnoreCase(editorial.getNombre());

        // isPresent(): Comrpueba si el contenedor Optional no tiene nulo
        // Si la editorial existe y esta presente en la BD

        if(existente.isPresent()){

            return "[-] La Editorial Ya Existe [X_X] ...";

        }

        // Si no existe, la guardamos:

        repository.save(editorial);

        return "[+] La Editorial Se Ha Guardado Correctamente :) ...";

    }


    // -- MÉTODO ACTUALIZAR EDITORIAL (PUT) -- //

    public String actualizar(Editorial editorial) {

        // Creamos una lista de editoriales:

        List<Editorial> listaEditorial = repository.findAll();

        // Recorremos la lista de editoriales:

        for (Editorial e : listaEditorial) {

            // Si coincide con la id de la editorial que buscamos

            if (e.getId().equals(editorial.getId())) {

                // guardamos la nueva editorial alli

                repository.save(editorial);

                return "[+] La Editorial Fue Actualizada Correctamente :) ... ";

            }

        }
        

        return "[-] Editorial No Encontrada [X_X] ... ";
    }

    // -- MÉTODO ELIMINAR EDITORIAL (DELETE) -- //

    public String eliminar(Integer id) {

        List<Editorial> listaEditorial = repository.findAll();

        for (Editorial editorial : listaEditorial) {
            if (editorial.getId().equals(id)) {
                repository.deleteById(id);
                return "[+] La Editorial Fue Eliminada Correctamente :) ... ";
            }
        }

        return "[-] Editorial No Encontrada [X_X] ... ";
    }

}
