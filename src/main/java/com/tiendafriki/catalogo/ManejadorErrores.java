package com.tiendafriki.catalogo;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;

import com.tiendafriki.catalogo.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

// Esta clase captura errores de toda la aplicación de forma globla
// Evita tener try-catch en cadacontroller
@RestControllerAdvice
public class ManejadorErrores {

    
    // === ERROR 400: VALIDACIONES JAKARTA - MALA SOLICITUD === //

    // Esta excepción ocurre automáticamente cuando fallan
    // las validaciones Jakarta del DTO.
    //
    // Ejemplos:
    // - Campos vacíos
    // - Correos inválidos
    // - Números negativos
    // - Strings demasiado largos
    //
    // Spring Boot detecta automáticamente los errores
    // antes de entrar al service.

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

    // === ERROR 404: NO ENCONTRADO === //

    // Esta excepción se utiliza cuando el recurso solicitado
    // no existe en la base de datos o en otro microservicio.
    //
    // Ejemplos:
    // - Pedido no encontrado
    // - Carrito inexistente
    // - Producto inexistente
    //
    // El service o controller lanzan esta excepción usando "throw"
    // y el manejador transforma automáticamente el error
    // en una respuesta HTTP 404 (Not Found).

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> manejarErroresNoEncontrado(
            NoSuchElementException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("error", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "[ERROR] Recurso No Encontrado [X_X]",
                errores,
                request.getRequestURI());

        return ResponseEntity.status(404).body(errorDTO);

    }

    // === ERRORES DE VALIDACIONES DE NEGOCIO === //

    // Esta excepción se utiliza para errores de lógica de negocio.
    // A diferencia de las validaciones Jakarta (@NotNull, @Email, etc),
    // estas validaciones dependen de reglas internas del sistema.
    //
    // Ejemplos:
    // - El total del pedido no coincide con el carrito
    // - Un producto no existe dentro del carrito
    // - El carrito no cumple una condición requerida
    //
    // El service lanza la excepción usando "throw" con su correspondiente mensaje
    // y este manejador se encarga de convertirla automáticamente
    // en una respuesta HTTP 400 (Bad Request).
    //
    // Gracias a esto:
    //
    // - El controller queda más limpio
    // - No es necesario devolver manualmente mensajes de error
    // - La lógica de errores queda centralizada

    @ExceptionHandler(IllegalArgumentException.class)

    public ResponseEntity<ErrorDTO> ErrorSolicitud(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(

                LocalDateTime.now(),

                400,

                // MOSTRAMOS EL MENSAJE REAL
                // enviado desde el service

                ex.getMessage(),

                null,

                request.getRequestURI());

        return ResponseEntity.badRequest().body(error);
    }
    

    // === ERROR 500: ERROR INTERNO DEL SERVIDOR === //

        // Esta excepción se utiliza para errores inesperados
        // ocurridos durante la ejecución del sistema.
        //
        // Generalmente ocurre cuando existe un problema
        // al comunicarse con otros microservicios.
        //
        // Ejemplos:
        //
        // - El microservicio carrito está apagado
        // - Falló la conexión HTTP
        // - Timeout de comunicación
        // - URL inexistente
        // - Error inesperado del servidor
        //
        // Normalmente estas excepciones ocurren dentro
        // de bloques try-catch del service.
        //
        // Cuando ocurre uno de estos errores,
        // se lanza un RuntimeException y este manejador
        // lo transforma automáticamente en un
        // HTTP 500 (Internal Server Error).

        @ExceptionHandler(RuntimeException.class)

        public ResponseEntity<ErrorDTO> manejarErrorInterno(
                RuntimeException ex,
                HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        errores.put("error", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(

                LocalDateTime.now(),

                500,

                "[ERROR] Error Interno del Servidor [X_X]",

                errores,

                request.getRequestURI());

        return ResponseEntity.status(500).body(errorDTO);
        }

}
