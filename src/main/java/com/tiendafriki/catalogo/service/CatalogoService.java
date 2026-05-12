package com.tiendafriki.catalogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiendafriki.catalogo.repository.*;
import com.tiendafriki.catalogo.dto.ProductoResumenDTO;
import com.tiendafriki.catalogo.model.*;
import java.util.Optional;
import java.util.List;

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

    private ProductoResumenDTO convertirADTO(Catalogo producto) {

        return new ProductoResumenDTO(
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

    public List<ProductoResumenDTO> listarDTO(){

        return repository.findAll()    // Obtiene todos los productos de la BD
            .stream()                  // Recorre la lista de productos
            .map(this::convertirADTO)  // Convierte cada producto a DTO mediante el método convertirADTO
            .toList(); // Convierte el resultado nuevamente en una lista

    }

    
    // -- (GET): BUSCAR POR ID -- //

    public Optional<ProductoResumenDTO> buscarPorId(Integer id) {

    return repository.findById(id)
            .map(this::convertirADTO); // Convierte el producto encontrado a DTO

    }

    // -- (GET): BUSCAR POR TITULO POR COINCIDENCIA -- //

    // Este método devolverá cualquier producto que coincida
    // con el titulo ingresado por el usuario

    public List<ProductoResumenDTO> buscarPorTitulo(String titulo) {

    return repository.findByTituloContainingIgnoreCase(titulo)
            .stream() // Recorre la lista de productos
            .map(this::convertirADTO) // Convierte cada producto a DTO
            .toList();

    }
    


    // -- (GET): BUSCAR POR GENERO -- //

    public List<ProductoResumenDTO> buscarPorGenero(String genero) {

    return repository.findByGeneroIgnoreCase(genero)
            .stream()
            .map(this::convertirADTO)
            .toList();

    }


    // -- (GET): BUSCAR POR AUTOR -- //

    public List<ProductoResumenDTO> buscarPorAutor(String autor) {

    return repository.findByAutorIgnoreCase(autor)
            .stream()
            .map(this::convertirADTO)
            .toList();

    }


    // -- (GET): BUSCAR POR CATEGORIA -- //

    public List<ProductoResumenDTO> buscarPorCategoria(String categoria) {

    return repository.findByCategoriaNombreIgnoreCase(categoria)
            .stream()
            .map(this::convertirADTO)
            .toList();

    }


    // -- (GET): BUSCAR POR EDITORIAL -- //

    public List<ProductoResumenDTO> buscarPorEditorial(String editorial) {

    return repository.findByEditorialNombreIgnoreCase(editorial)
            .stream()
            .map(this::convertirADTO)
            .toList();

    }




    // -- (GET): BUSCAR POR AÑO -- //

    public List<ProductoResumenDTO> buscarPorAnio(Integer anio) {

    return repository.findByAnio(anio)
            .stream()
            .map(this::convertirADTO)
            .toList();

    }



    // -- MÉTODO GUARDAR PRODUCTO (POST) -- //

    public String guardar(Catalogo producto) {

        // Validar que el producto NO exista:

        // buscamos por titulo exacto en el repository y lo guardamos en existente

        Optional<Catalogo> existente = repository.findByTituloIgnoreCase(producto.getTitulo());

        // isPresent(): Comrpueba si el contenedor Optional no tiene nulo
        // Comprobamos que el producto existente no sea nulo y este presente en la base de datos

        if(existente.isPresent()){

            return "[-] El producto del catalogo ya existe [X_X] ...";

        }

        // Validar que la CATEGORÍA ingresada EXISTA:

        if (producto.getCategoria() != null) {
            Optional<Categoria> catOpt = categoriaRepo.findById(
                producto.getCategoria().getId()
            );

            if (catOpt.isEmpty()) {
                return "[-] Categoria No Encontrada [X_X] ... ";
            }

            producto.setCategoria(catOpt.get());
        }

        // Validar que la EDITORIAL ingresada EXISTA:

        if (producto.getEditorial() != null) {
            Optional<Editorial> edOpt = editorialRepo.findById(
                    producto.getEditorial().getId()
            );

            if (edOpt.isEmpty()) {
                return "[-] Editorial No Encontrada [X_X] ... ";
            }

            producto.setEditorial(edOpt.get());
        }

        repository.save(producto);

        return "[+] El Producto se a agregado correctamente al Catalogo ... ";

    }

    // -- MÉTODO ACTUALIZAR PRODUCTO (PUT)-- //

    public String actualizar(Catalogo producto) {

        // Creamos una lista de productos del catalogo:

        List<Catalogo> listaCatalogo = repository.findAll();

        // Recorremos la lista de productos del catalogo

        for (Catalogo p : listaCatalogo) {

            // Si coincide con la id del producto que buscamos
            if (p.getId().equals(producto.getId())) {

                // Validar si existe la categoría:

                if (producto.getCategoria() != null) {
                    Optional<Categoria> catOpt = categoriaRepo.findById(
                            producto.getCategoria().getId()
                    );

                    if (catOpt.isEmpty()) {
                        return "[-] Categoria No Encontrada [X_X] ... ";
                    }

                    producto.setCategoria(catOpt.get());
                }

                // Validar si existe la editorial:
                if (producto.getEditorial() != null) {
                    Optional<Editorial> edOpt = editorialRepo.findById(
                            producto.getEditorial().getId()
                    );

                    if (edOpt.isEmpty()) {
                        return "[-] Editorial No Encontrada [X_X] ... ";
                    }

                    producto.setEditorial(edOpt.get());
                }

                repository.save(producto);

                return "[+] El Producto Del Catalogo Fue Actualizado ... ";
            }
        }
        

        return "[-] Producto En El Catalogo No Encontrado [X_X] ... ";
    }

    // -- MÉTODO ELIMINAR PRODUCTO (DELETE) -- //

    public String eliminar(Integer id) {

        List<Catalogo> listaCatalogo = repository.findAll();

        for (Catalogo producto : listaCatalogo) {
            if (producto.getId().equals(id)) {
                repository.deleteById(id);
                return "[+] Producto Eliminado Del Catalogo ... ";
            }
        }

        return "[-] Producto No Encontrado En El Catalogo [X_X] ... ";
    }


    /*

    // ========================== MÉTODOS (GET) SIN DTO ========================== //

    // -- GET: LISTAR TODOS LOS PRODUCTOS (SIN DTO):  -- //

    public List<Catalogo> listar() {
        return repository.findAll();
    }

     // -- BUSCAR POR ID -- //

    public Optional<Catalogo> buscarPorId(Integer id) {
        
        return repository.findById(id);
    }

    // -- BUSCAR POR TITULO POR COINCIDENCIA -- //

    // Este método devolverá cualquier producto que coincida
    // con el titulo ingresado por el usuario

    public List<Catalogo> buscarPorTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    // -- BUSCAR POR GENERO -- //

    public List<Catalogo> buscarPorGenero(String genero) {
        return repository.findByGeneroIgnoreCase(genero);
    }

    // -- BUSCAR POR AUTOR -- //

    public List<Catalogo> buscarPorAutor(String autor) {
        return repository.findByAutorIgnoreCase(autor);
    }

    // -- BUSCAR POR CATEGORIA  -- //

    public List<Catalogo> buscarPorCategoria(String categoria) {
        return repository.findByCategoriaNombreIgnoreCase(categoria);
    }

    // -- BUSCAR POR EDITORIAL -- //

    public List<Catalogo> buscarPorEditorial(String editorial) {
        return repository.findByEditorialNombreIgnoreCase(editorial);
    }

    // -- BUSCAR POR AÑO -- //

    public List<Catalogo> buscarPorAnio(Integer anio) {
        return repository.findByAnio(anio);
    }

    // =========================================================================== //

    */


}

