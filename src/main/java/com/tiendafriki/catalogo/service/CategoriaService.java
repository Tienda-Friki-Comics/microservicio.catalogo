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

    // -- LISTAR TODAS LAS CATEGORIAS -- //
    public List<Categoria> listar() {

        return repository.findAll();
    }

    // -- BUSCAR POR ID -- //

    public Categoria buscarPorId(Integer id) {

        return repository.findById(id)
                        .orElseThrow(() ->
                        new NoSuchElementException
                        ( "[ERROR] Categoria No Encontrada [X_X] ..."));
    }

    // -- BUSCAR POR NOMBRE CATEGORIA  -- //

    public Categoria buscarPorNombre(String nombre) {

        return repository.findByNombreIgnoreCase(nombre)
                        .orElseThrow(() ->
                        new NoSuchElementException
                        ( "[ERROR] Categoria No Encontrada [X_X] ..."));
    }

    // -- CREAR CATEGORIA -- //

    public String guardar(Categoria categoria){

        // Validar que NO exista: //

        // buscamos por el nombre exacto en el repository y lo guardamos en existente

        Optional<Categoria> existente = repository.findByNombreIgnoreCase(categoria.getNombre());

        // isPresent(): Comrpueba si el contenedor Optional no tiene nulo
        // Si la categoría existe y esta presente en la BD

        if(existente.isPresent()){

            throw new IllegalArgumentException(
                    "[ERROR] La categoria ya existe [X_X] ...");

        }

        // Si no existe, la guardamos:

        repository.save(categoria);

        return "[+] La Categoria Se Ha Guardado Correctamente ...";

    }


    // -- MÉTODO ACTUALIZAR CATEGORIA (PUT) -- //

    public String actualizar(Integer id, Categoria categoria) {

        // Buscamos la categoría por id

        Optional<Categoria> cateOpt = repository.findById(id);

        // Validamos existencia

        // isEmpty(): Comprueba si el contenedor Optional esta vacio

        if(cateOpt.isEmpty()){

            // En caso de no existir, devolvemos mensaje de error

            throw new NoSuchElementException(
                    "[ERROR] Categoria No Encontrada [X_X] ...");

        }

        // En caso contrario, guardamos la categoria actualizada

        repository.save(categoria);

        return "[+] La Categoria Fue Actualizada Correctamente ... ";

    }

    // -- MÉTODO ELIMINAR CATEGORIA (DELETE) -- //

    public String eliminar(Integer id) {

        // Buscamos la editorial por id

        Optional<Categoria> CateOpt = repository.findById(id);

        // Validamos existencia

        if(CateOpt.isEmpty()){

            // En caso de no existir, devolvemos mensaje de error

            throw new NoSuchElementException(
                    "[ERROR] Categoria No Encontrada [X_X] ...");

        }

        // === VALIDAR SI LA CATEGORIA ESTA SIENDO USADA === //

        // Si existe algun producto asociado a esta categoria,
        // NO permitimos eliminarla

        if (catalogoRepo.existsByCategoriaId(id)) {

            throw new IllegalArgumentException(
                    "[ERROR] No se puede eliminar la categoria porque tiene productos asociados [X_X] ..."
            );
        }

        // En caso contrario, eliminamos la editorial

        repository.deleteById(id);

        return "[+] La Categoria Fue Eliminada Correctamente ... ";

    }
    
}
