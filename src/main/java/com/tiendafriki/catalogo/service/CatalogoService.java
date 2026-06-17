package com.tiendafriki.catalogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiendafriki.catalogo.repository.*;
import com.tiendafriki.catalogo.dto.ProductoRequestDTO;
import com.tiendafriki.catalogo.dto.ProductoResponseDTO;
import com.tiendafriki.catalogo.model.*;
import java.util.Optional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CatalogoService {

    @Autowired
    private CatalogoRepo repository;

    @Autowired
    private CategoriaRepo categoriaRepo;

    @Autowired
    private EditorialRepo editorialRepo;

    private ProductoResponseDTO convertirADTO(Catalogo producto) {

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getTitulo(),
                producto.getGenero(),
                producto.getAnio(),
                producto.getAutor(),
                producto.getEditorial().getNombre(),
                producto.getCategoria().getNombre(), 
                producto.getStock(),
                producto.getPrecio()
        );
    }

    public List<ProductoResponseDTO> listar(){

        return repository.findAll()    
            .stream()                  
            .map(this::convertirADTO)  
            .toList(); 


    }

    public ProductoResponseDTO buscarPorId(Integer id) {

    return repository.findById(id)
            .map(this::convertirADTO) 
            .orElseThrow(() ->
                new NoSuchElementException
                ( "[ERROR] Producto no encontrado [X_X] ..."));

    }

    public List<ProductoResponseDTO> buscarPorTitulo(String titulo) {

        List<Catalogo> productos = repository.findByTituloContainingIgnoreCase(titulo);

        if (productos.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos con ese titulo [X_X] ...");

        }

        return productos.stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<ProductoResponseDTO> buscarPorGenero(String genero) {

        List<Catalogo> productos = repository.findByGeneroIgnoreCase(genero);
                
        if (productos.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por ese genero [X_X] ...");

        }
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();

    }

    public List<ProductoResponseDTO> buscarPorAutor(String autor) {

        List<Catalogo> productos = repository.findByAutorIgnoreCase(autor);

        if (productos.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por ese autor [X_X] ...");

        }
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();
            
    }

    public List<ProductoResponseDTO> buscarPorCategoria(String categoria) {

        List<Catalogo> productos = repository.findByCategoriaNombreIgnoreCase(categoria);

        if (productos.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por esa categoría [X_X] ...");

        }
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();

    }


    public List<ProductoResponseDTO> buscarPorEditorial(String editorial) {

        List<Catalogo> productos = repository.findByEditorialNombreIgnoreCase(editorial);

        if (productos.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por esa editorial [X_X] ...");

        }
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();

    }

    public List<ProductoResponseDTO> buscarPorAnio(Integer anio) {

        List<Catalogo> productos = repository.findByAnio(anio);

        if (productos.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por ese año [X_X] ...");

        }
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();

    }

    public String guardar(ProductoRequestDTO productoDTO) {

        Optional<Catalogo> existente =
                repository.findByTituloIgnoreCase(
                        productoDTO.getTitulo()
                );

        if (existente.isPresent()) {

            throw new IllegalArgumentException(
                    "[ERROR] El producto del catalogo ya existe [X_X] ...");
        }

        Optional<Categoria> catOpt =
                categoriaRepo.findByNombreIgnoreCase(
                        productoDTO.getCategoria()
                );

        if (catOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Categoría No Encontrada [X_X] ...");
        }

        Optional<Editorial> edOpt =
                editorialRepo.findByNombreIgnoreCase(
                        productoDTO.getEditorial()
                );

        if (edOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Editorial No Encontrada [X_X] ..."
            );
        }

        Catalogo producto = new Catalogo();

        producto.setTitulo(productoDTO.getTitulo());

        producto.setGenero(productoDTO.getGenero());

        producto.setAnio(productoDTO.getAnio());

        producto.setAutor(productoDTO.getAutor());

        producto.setStock(productoDTO.getStock());

        producto.setPrecio(productoDTO.getPrecio());

        producto.setCategoria(catOpt.get());

        producto.setEditorial(edOpt.get());

        repository.save(producto);

        return "[+] El Producto se agregó correctamente al catálogo ... ";
    }

    public String actualizar(Integer id, ProductoRequestDTO productoDTO) {

        Optional<Catalogo> productoOpt =
                repository.findById(id);

        if (productoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Producto No Encontrado en el Catalogo [X_X] ... "
            );
        }

        Optional<Categoria> catOpt =
                categoriaRepo.findByNombreIgnoreCase(
                        productoDTO.getCategoria()
                );

        if (catOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Categoria No Encontrada [X_X] ..."
            );
        }

        Optional<Editorial> edOpt =
                editorialRepo.findByNombreIgnoreCase(
                        productoDTO.getEditorial()
                );

        if (edOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Editorial No Encontrada [X_X] ..."
            );
        }

        Catalogo producto = productoOpt.get();

        producto.setTitulo(productoDTO.getTitulo());

        producto.setGenero(productoDTO.getGenero());

        producto.setAnio(productoDTO.getAnio());

        producto.setAutor(productoDTO.getAutor());

        producto.setStock(productoDTO.getStock());

        producto.setPrecio(productoDTO.getPrecio());

        producto.setCategoria(catOpt.get());

        producto.setEditorial(edOpt.get());

        repository.save(producto);

        return "[+] El Producto Del Catalogo Fue Actualizado ... ";
    }

    public String eliminar(Integer id) {

        Optional<Catalogo> productoOpt =
                repository.findById(id);

        if (productoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Producto No Encontrado en el Catalogo [X_X] ... ");
        }

        repository.deleteById(id);

        return "[+] Producto Eliminado Del Catalogo ... ";
    }

    public String descontarStock(Integer productoId, Integer cantidad) {

        Optional<Catalogo> productoOpt =
                repository.findById(productoId);

        if (productoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Producto no encontrado [X_X] ..."
            );
        }

        Catalogo producto = productoOpt.get();

        if (producto.getStock() < cantidad) {

            throw new IllegalArgumentException(
                    "[ERROR] Stock insuficiente para el producto "
                            + producto.getTitulo()
                            + " [X_X] ..."
            );
        }

        producto.setStock(producto.getStock() - cantidad);

        repository.save(producto);

        return "[+] Stock actualizado correctamente";
    }

}

