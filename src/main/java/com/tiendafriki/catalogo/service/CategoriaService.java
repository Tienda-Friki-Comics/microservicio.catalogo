package com.tiendafriki.catalogo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendafriki.catalogo.model.Categoria;
import com.tiendafriki.catalogo.repository.CatalogoRepo;
import com.tiendafriki.catalogo.repository.CategoriaRepo;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepo repository;

    @Autowired
    private CatalogoRepo catalogoRepo;

    public List<Categoria> listar() {

        return repository.findAll();
    }

    public Categoria buscarPorId(Integer id) {

        return repository.findById(id)
                        .orElseThrow(() ->
                        new NoSuchElementException
                        ( "[ERROR] Categoria No Encontrada [X_X] ..."));
    }

    public Categoria buscarPorNombre(String nombre) {

        return repository.findByNombreIgnoreCase(nombre)
                        .orElseThrow(() ->
                        new NoSuchElementException
                        ( "[ERROR] Categoria No Encontrada [X_X] ..."));
    }

    public String guardar(Categoria categoria){

        Optional<Categoria> existente = repository.findByNombreIgnoreCase(categoria.getNombre());

        if(existente.isPresent()){

            throw new IllegalArgumentException(
                    "[ERROR] La categoria ya existe [X_X] ...");

        }

        repository.save(categoria);

        return "[+] La Categoria Se Ha Guardado Correctamente ...";

    }

    public String actualizar(Integer id, Categoria categoria) {

        Optional<Categoria> cateOpt = repository.findById(id);

        if(cateOpt.isEmpty()){

            throw new NoSuchElementException(
                    "[ERROR] Categoria No Encontrada [X_X] ...");

        }

        repository.save(categoria);

        return "[+] La Categoria Fue Actualizada Correctamente ... ";

    }

    public String eliminar(Integer id) {

        Optional<Categoria> CateOpt = repository.findById(id);

        if(CateOpt.isEmpty()){

            throw new NoSuchElementException(
                    "[ERROR] Categoria No Encontrada [X_X] ...");

        }

        if (catalogoRepo.existsByCategoriaId(id)) {

            throw new IllegalArgumentException(
                    "[ERROR] No se puede eliminar la categoria porque tiene productos asociados [X_X] ..."
            );
        }

        repository.deleteById(id);

        return "[+] La Categoria Fue Eliminada Correctamente ... ";

    }
    
}
