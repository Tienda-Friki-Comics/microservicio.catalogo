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



/*

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.tiendafriki.catalogo.repository.*;
import com.tiendafriki.catalogo.model.*;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(CatalogoRepo repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Catalogo(
                    null, 
                    "Harry Potter y la piedra filosofal", 
                    "Novela", 
                    1997, 
                    "Salamandra", 
                    "J.K Rowling",
                    "Libro",
                    26,
                    23000
                ));
                repo.save(new Catalogo(
                    null,
                    "Harry Potter y camara de lso secretos ",
                    "Novela",
                    1998,
                    "Salamandra",
                    "J.K. Rowling",
                    "Libro",
                    86,
                    54000
                ));
                repo.save(new Catalogo(
                    null,
                    "Harry Potter y el prisionero de Azkaban",
                    "Novela",
                    1999,
                    "Salamandra",
                    "J.K. Rowling",
                    "Libro",
                    98,
                    10000
                ));
                repo.save(new Catalogo(
                    null,
                    "Harry Potter y el cáliz de fuego",
                    "Novela",
                    2000,
                    "Salamandra",
                    "J.K. Rowling",
                    "Libro",
                    34,
                    20000
                ));
                repo.save(new Catalogo(
                    null,
                    "Harry Potter y la Orden del Fénix",
                    "Novela",
                    2003,
                    "Salamandra",
                    "J.K. Rowling",
                    "Libro",
                    45,
                    87000
                ));
                repo.save(new Catalogo(
                    null,
                    "Harry Potter y el misterio del príncipe",
                    "Fantasia",
                    2005,
                    "Salamandra",
                    "J.K. Rowling",
                    "Libro",
                    99,
                    54000
                ));
                repo.save(new Catalogo(
                    null,
                    "Harry Potter y las reliquias de la muerte",
                    "Fantasia",
                    2007,
                    "Salamandra",
                    "J.K. Rowling",
                    "Libro",
                    12,
                    87000
                ));
                repo.save(new Catalogo(
                    null,
                    "El Hobbit",
                    "Fantasia",
                    1937,
                    "George Allen & Unwin",
                    "J.R.R. Tolkien",
                    "Libro",
                    14,
                    43000
                ));
                repo.save(new Catalogo(
                    null,
                    "El Señor De Los Anillos: La Comunidad del Anillo",
                    "Fantasia Epica",
                    1954,
                    "George Allen & Unwin",
                    "J.R.R. Tolkien",
                    "Libro",
                    100,
                    12000
                ));
                repo.save(new Catalogo(
                    null,
                    "El Señor De Los Anillos: Las Dos Torres",
                    "Fantasia Epica",
                    1954,
                    "George Allen & Unwin",
                    "J.R.R. Tolkien",
                    "Libro",
                    92,
                    30000
                ));
                repo.save(new Catalogo(
                    null,
                    "El Señor De Los Anillos: El Retorno Del Rey",
                    "Fantasia Epica",
                    1955,
                    "George Allen & Unwin",
                    "J.R.R. Tolkien",
                    "Libro",
                    199,
                    20000
                ));
                repo.save(new Catalogo(
                    null,
                    "Naruto",
                    "Shonen",
                    1999,
                    "Shueisha",
                    "Masashi Kishimoto",
                    "Manga",
                    15,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "One Piece",
                    "Aventura",
                    1997,
                    "Shueisha",
                    "Eiichiro Oda",
                    "Manga",
                    180,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "Dragon Ball",
                    "Shonen",
                    1984,
                    "Shueisha",
                    "Akira Toriyama",
                    "Manga",
                    13,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "My Hero Academia",
                    "Superhèroes",
                    2014,
                    "Shueisha",
                    "Kohei Horikoshi",
                    "Manga",
                    13,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "Kimetsu No Yaiba",
                    "Històrico",
                    2016,
                    "Shueisha",
                    "Koyoharu Gotouge",
                    "Manga",
                    89,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "Fullmetal Alchemist",
                    "Acciòn",
                    2001,
                    "Square Enix",
                    "Himoru Arakawa",
                    "Manga",
                    85,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "Sailor Moon",
                    "Magical Girl",
                    1991,
                    "Kodansha",
                    "Naoko Takeuchi",
                    "Manga",
                    18,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "Gashiakuta",
                    "Fantasia Oscura",
                    2022,
                    "Kodansha",
                    "Kei Urana",
                    "Manga",
                    65,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "Dandadan",
                    "Sobrenatural",
                    2021,
                    "Shueisha",
                    "Yukinobu Tatsu",
                    "Manga",
                    78,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "Pokèmon Adventures",
                    "Aventura",
                    1997,
                    "Shogakukan",
                    "Hidenori Kusaka / Satoshi Yamamoto",
                    "Manga",
                    23,
                    10000
                 ));
                 repo.save(new Catalogo(
                    null,
                    "The Eminence In Shadow",
                    "Isekai",
                    2018,
                    "Kadokawa",
                    "Anri Sakano",
                    "Manga",
                    78,
                    10000
                 ));
            }
        };
    }
}


*/