package com.tiendafriki.catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendafriki.catalogo.model.Categoria;
import com.tiendafriki.catalogo.repository.CategoriaRepo;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepo repository;

    // -- LISTAR TODAS LAS CATEGORIAS -- //
    public List<Categoria> listar() {
        return repository.findAll();
    }

    // -- BUSCAR POR ID -- //

    public Optional<Categoria> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    // -- BUSCAR POR NOMBRE CATEGORIA  -- //

    public Optional<Categoria> buscarPorNombre(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }

    // -- CREAR CATEGORIA -- //

    public String guardar(Categoria categoria){

        // Validar que NO exista: //

        // buscamos por el nombre exacto en el repository y lo guardamos en existente

        Optional<Categoria> existente = repository.findByNombreIgnoreCase(categoria.getNombre());

        // isPresent(): Comrpueba si el contenedor Optional no tiene nulo
        // Si la categoría existe y esta presente en la BD

        if(existente.isPresent()){

            return "[-] La Categoria Ya Existe [X_X] ...";

        }

        // Si no existe, la guardamos:

        repository.save(categoria);

        return "[+] La Categoria Se Ha Guardado Correctamente :) ...";

    }


    // -- MÉTODO ACTUALIZAR CATEGORIA (PUT) -- //

    public String actualizar(Categoria categoria) {

        // Creamos una lista de categorias:

        List<Categoria> listaCategoria = repository.findAll();

        // Recorremos la lista de categorias:

        for (Categoria c : listaCategoria) {

            // Si coincide con la id de la categoria que buscamos

            if (c.getId().equals(categoria.getId())) {

                // guardamos la nueva categoria alli

                repository.save(categoria);

                return "[+] La Categoria Fue Actualizada Correctamente :) ... ";

            }

        }
        

        return "[-] Categoria No Encontrada [X_X] ... ";
    }

    // -- MÉTODO ELIMINAR CATEGORIA (DELETE) -- //

    public String eliminar(Integer id) {

        List<Categoria> listaCategoria = repository.findAll();

        for (Categoria categoria : listaCategoria) {
            if (categoria.getId().equals(id)) {
                repository.deleteById(id);
                return "[+] La Categoria Fue Eliminada Correctamente :) ... ";
            }
        }

        return "[-] Categoria No Encontrada [X_X] ... ";
    }
    
}
