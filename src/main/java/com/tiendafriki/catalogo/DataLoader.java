package com.tiendafriki.catalogo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import com.tiendafriki.catalogo.repository.*;
import com.tiendafriki.catalogo.model.*;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(CatalogoRepo catalogoRepo,
                           EditorialRepo editorialRepo,
                           CategoriaRepo categoriaRepo) {

        return args -> {

            if (catalogoRepo.count() == 0) {

                // Editoriales
                Editorial salamandra = editorialRepo.findByNombreIgnoreCase("Salamandra")
                        .orElseGet(() -> editorialRepo.save(new Editorial(null, "Salamandra")));

                Editorial shueisha = editorialRepo.findByNombreIgnoreCase("Shueisha")
                        .orElseGet(() -> editorialRepo.save(new Editorial(null, "Shueisha")));

                Editorial allen = editorialRepo.findByNombreIgnoreCase("George Allen & Unwin")
                        .orElseGet(() -> editorialRepo.save(new Editorial(null, "George Allen & Unwin")));

                // Categorías
                Categoria libro = categoriaRepo.findByNombreIgnoreCase("Libro")
                        .orElseGet(() -> categoriaRepo.save(new Categoria(null, "Libro")));

                Categoria manga = categoriaRepo.findByNombreIgnoreCase("Manga")
                        .orElseGet(() -> categoriaRepo.save(new Categoria(null, "Manga")));

                // Datos
                catalogoRepo.save(new Catalogo(null,
                        "Harry Potter y la piedra filosofal",
                        "Novela",
                        1997,
                        "J.K Rowling",
                        salamandra,
                        libro,
                        26,
                        23000));

                catalogoRepo.save(new Catalogo(null,
                        "El Hobbit",
                        "Fantasia",
                        1937,
                        "J.R.R. Tolkien",
                        allen,
                        libro,
                        14,
                        43000));

                catalogoRepo.save(new Catalogo(null,
                        "Naruto",
                        "Shonen",
                        1999,
                        "Masashi Kishimoto",
                        shueisha,
                        manga,
                        15,
                        10000));

                catalogoRepo.save(new Catalogo(null,
                        "One Piece",
                        "Aventura",
                        1997,
                        "Eiichiro Oda",
                        shueisha,
                        manga,
                        180,
                        10000));
            }
        };
    }
}