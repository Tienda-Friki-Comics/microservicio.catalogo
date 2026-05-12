package com.tiendafriki.catalogo;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import com.tiendafriki.catalogo.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

// Esta clase captura errores de toda la aplicación de forma globla
// Evita tener try-catch en cadacontroller
@RestControllerAdvice
public class ManejadorErrores {

    // Manejo de errores de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Indica qu este metodo se ejecuta automaticamente cuando ocurre una excepcion de validacion
    public ResponseEntity<ErrorDTO> manejarErroresValidacion(

           MethodArgumentNotValidException ex, // contiene el detalle de los errores
           HttpServletRequest request) { // permite obtener información del request (la url)

        // Mapa donde se almacenan los errores por campo (eje: "nombre"  : "no puede estar vacio")

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> { // se recorren todos los errores de validación

            errores.put(error.getField(), error.getDefaultMessage()); // Se guarda el nombre del campo y su mensaje

        });

        // Se crea un objeto ErrorDTO con la información del error
        ErrorDTO errorDTO = new ErrorDTO(

            LocalDateTime.now(),                // fecha y hora del error
            400,                        // Codigo http 400 = bad request
            "Error de validación",     // Mensaje general
            errores,                            // detalle de errores por campo
            request.getRequestURI()             // URL del endpoint donde fallo

        );

        // Se construye la respuesta HTTP:
        // badRequest(): establece el estado HTTP 400
        // body(errorDTO): envia el objeto ErrorDTO coomo respuesta JSON

        return ResponseEntity.badRequest().body(errorDTO);

    }

    // Manejo de errores GENERALES
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarErroresGenerales(
            Exception ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(

                LocalDateTime.now(),                               // fecha y hora
                500,                                       // status HTTP
                "[-] Error interno del servidor [x_x]",  // mensaje de error de servidor
                null,                                    // no hay errores por campo
                request.getRequestURI()                          // endpoint donde ocurrió

        );

        return ResponseEntity.status(500).body(error);
    }

}
