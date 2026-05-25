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

    // -- MÉTODO PARA CONVERTIR UNA ENTIDAD CATALOGO A DTO -- //

    // Este método adaptará el objeto producto al formato del ProductoResumenDTO

    private ProductoResponseDTO convertirADTO(Catalogo producto) {

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getTitulo(),
                producto.getGenero(),
                producto.getAnio(),
                producto.getAutor(),
                producto.getEditorial().getNombre(), // Obtiene solo el nombre de la editorial
                producto.getCategoria().getNombre(), // Obtiene solo el nombre de la categoria
                producto.getStock(),
                producto.getPrecio()
        );
    }

    // -- (GET): LISTAR PRODUCTOS DEL CATÁLOGO (CON DTO) -- //

    // Este método devuelve el listado de productos por medio del DTO
    // que resume los datos de los productos

    public List<ProductoResponseDTO> listar(){

        return repository.findAll()    // Obtiene todos los productos de la BD
            .stream()                  // Recorre la lista de productos
            .map(this::convertirADTO)  // Convierte cada producto a DTO mediante el método convertirADTO
            .toList(); // Convierte el resultado nuevamente en una lista


    }

    
    // -- (GET): BUSCAR POR ID -- //

    public ProductoResponseDTO buscarPorId(Integer id) {

    return repository.findById(id)
            .map(this::convertirADTO) // Convierte el producto encontrado a DTO
            .orElseThrow(() ->
                new NoSuchElementException
                ( "[ERROR] Producto no encontrado [X_X] ..."));

    }

    // -- (GET): BUSCAR POR TITULO POR COINCIDENCIA -- //

    // Este método devolverá cualquier producto que coincida
    // con el titulo ingresado por el usuario

    public List<ProductoResponseDTO> buscarPorTitulo(String titulo) {

        // Buscamos productos por titulo
        // y lo guardamos en una lista de productos de catalogo

        List<Catalogo> productos = repository.findByTituloContainingIgnoreCase(titulo);

        // Validamos si la lista de productos esta vacia

        if (productos.isEmpty()) {

            // Si la lista esta vacia, entonces no hay productos con ese titulo

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos con ese titulo [X_X] ...");

        }

        // En caso contrario,
        // Convertimos la lista de productos al formato del DTO y lo retornamos

        return productos.stream()
                .map(this::convertirADTO)
                .toList();
    }
    

    // -- (GET): BUSCAR POR GENERO -- //

    public List<ProductoResponseDTO> buscarPorGenero(String genero) {

        List<Catalogo> productos = repository.findByGeneroIgnoreCase(genero);
                
        if (productos.isEmpty()) {

            // Si la lista esta vacia, devolvemos mensaje no encontrado

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por ese genero [X_X] ...");

        }

        // En caso contrario,
        // Convertimos la lista de productos al formato del DTO y lo retornamos
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();

    }


    // -- (GET): BUSCAR POR AUTOR -- //

    public List<ProductoResponseDTO> buscarPorAutor(String autor) {

        // Se hace una lista de productos y se busca por el parametro indicado

        List<Catalogo> productos = repository.findByAutorIgnoreCase(autor);

        // Si la lista esta vacia

        if (productos.isEmpty()) {

            // Devolvera mensaje de no encontrado

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por ese autor [X_X] ...");

        }

        // En caso contrario,
        // Convertimos la lista de productos al formato del DTO y lo retornamos
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();
            
    }


    // -- (GET): BUSCAR POR CATEGORIA -- //

    public List<ProductoResponseDTO> buscarPorCategoria(String categoria) {

        // Se hace una lista de productos y se busca por el parametro indicado

        List<Catalogo> productos = repository.findByCategoriaNombreIgnoreCase(categoria);
        
        // Si la lista esta vacia

        if (productos.isEmpty()) {

            // Devolvera mensaje de no encontrado

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por esa categoría [X_X] ...");

        }

        // En caso contrario,
        // Convertimos la lista de productos al formato del DTO y lo retornamos
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();

    }


    // -- (GET): BUSCAR POR EDITORIAL -- //

    public List<ProductoResponseDTO> buscarPorEditorial(String editorial) {

        List<Catalogo> productos = repository.findByEditorialNombreIgnoreCase(editorial);

        // Si la lista esta vacia

        if (productos.isEmpty()) {

            // Devolvera mensaje de no encontrado

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por esa editorial [X_X] ...");

        }

        // En caso contrario,
        // Convertimos la lista de productos al formato del DTO y lo retornamos
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();

    }


    // -- (GET): BUSCAR POR AÑO -- //

    public List<ProductoResponseDTO> buscarPorAnio(Integer anio) {

        List<Catalogo> productos = repository.findByAnio(anio);
        
        // Si la lista esta vacia

        if (productos.isEmpty()) {

            // Devolvera mensaje de no encontrado

            throw new NoSuchElementException(
                    "[ERROR] No se encontraron productos por ese año [X_X] ...");

        }

        // En caso contrario,
        // Convertimos la lista de productos al formato del DTO y lo retornamos
        
        return productos.stream()
                .map(this::convertirADTO)
                .toList();

    }

    
    // === MÉTODO GUARDAR PRODUCTO (POST) === //

    public String guardar(ProductoRequestDTO productoDTO) {

        // Validar que el producto NO exista

        Optional<Catalogo> existente =
                repository.findByTituloIgnoreCase(
                        productoDTO.getTitulo()
                );

        if (existente.isPresent()) {

            throw new IllegalArgumentException(
                    "[ERROR] El producto del catalogo ya existe [X_X] ...");
        }

        // === VALIDAR CATEGORIA === //

        Optional<Categoria> catOpt =
                categoriaRepo.findByNombreIgnoreCase(
                        productoDTO.getCategoria()
                );

        if (catOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Categoría No Encontrada [X_X] ...");
        }

        // === VALIDAR EDITORIAL === //

        Optional<Editorial> edOpt =
                editorialRepo.findByNombreIgnoreCase(
                        productoDTO.getEditorial()
                );

        if (edOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Editorial No Encontrada [X_X] ..."
            );
        }

        // === CREAR OBJETO CATALOGO === //

        Catalogo producto = new Catalogo();

        producto.setTitulo(productoDTO.getTitulo());

        producto.setGenero(productoDTO.getGenero());

        producto.setAnio(productoDTO.getAnio());

        producto.setAutor(productoDTO.getAutor());

        producto.setStock(productoDTO.getStock());

        producto.setPrecio(productoDTO.getPrecio());

        // Convertimos String -> Entidad real

        producto.setCategoria(catOpt.get());

        producto.setEditorial(edOpt.get());

        // Guardamos producto

        repository.save(producto);

        return "[+] El Producto se agregó correctamente al catálogo ... ";
    }

    // === MÉTODO ACTUALIZAR PRODUCTO (PUT) === //

    public String actualizar(Integer id, ProductoRequestDTO productoDTO) {

        // Buscamos el producto por ID

        Optional<Catalogo> productoOpt =
                repository.findById(id);

        // Validamos existencia

        if (productoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Producto No Encontrado en el Catalogo [X_X] ... "
            );
        }

        // === VALIDAR CATEGORIA === //

        Optional<Categoria> catOpt =
                categoriaRepo.findByNombreIgnoreCase(
                        productoDTO.getCategoria()
                );

        if (catOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Categoria No Encontrada [X_X] ..."
            );
        }

        // === VALIDAR EDITORIAL === //

        Optional<Editorial> edOpt =
                editorialRepo.findByNombreIgnoreCase(
                        productoDTO.getEditorial()
                );

        if (edOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Editorial No Encontrada [X_X] ..."
            );
        }

        // Obtenemos el producto real

        Catalogo producto = productoOpt.get();

        // Actualizamos datos

        producto.setTitulo(productoDTO.getTitulo());

        producto.setGenero(productoDTO.getGenero());

        producto.setAnio(productoDTO.getAnio());

        producto.setAutor(productoDTO.getAutor());

        producto.setStock(productoDTO.getStock());

        producto.setPrecio(productoDTO.getPrecio());

        // Convertimos String -> Entidad

        producto.setCategoria(catOpt.get());

        producto.setEditorial(edOpt.get());

        // Guardamos cambios

        repository.save(producto);

        return "[+] El Producto Del Catalogo Fue Actualizado ... ";
    }

    // -- MÉTODO ELIMINAR PRODUCTO (DELETE) -- //

    public String eliminar(Integer id) {

        // Buscamos el producto por ID

        Optional<Catalogo> productoOpt =
                repository.findById(id);

        // Validamos existencia

        if (productoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Producto No Encontrado en el Catalogo [X_X] ... ");
        }

        // Eliminamos producto

        repository.deleteById(id);

        return "[+] Producto Eliminado Del Catalogo ... ";
    }


    // === MÉTODO PARA DESCONTAR STOCK AUTOMATICAMENTE (PUT) === //

    public String descontarStock(Integer productoId, Integer cantidad) {

        // Buscamos el producto por ID

        Optional<Catalogo> productoOpt =
                repository.findById(productoId);

        // Validamos existencia del producto

        if (productoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Producto no encontrado [X_X] ..."
            );
        }

        // Obtenemos el producto encontrado

        Catalogo producto = productoOpt.get();

        // === VALIDAR STOCK DISPONIBLE === //

        // Si el stock actual es menor
        // a la cantidad solicitada

        if (producto.getStock() < cantidad) {

            throw new IllegalArgumentException(
                    "[ERROR] Stock insuficiente para el producto "
                            + producto.getTitulo()
                            + " [X_X] ..."
            );
        }

        // === DESCONTAR STOCK === //

        producto.setStock(producto.getStock() - cantidad);

        // Guardamos cambios

        repository.save(producto);

        return "[+] Stock actualizado correctamente";
    }

}

