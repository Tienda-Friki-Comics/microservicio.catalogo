package com.tiendafriki.catalogo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorDTO {

    private LocalDateTime timeStamp; // Fecha y hora que ocurrió el error
    private int status; // codigo de estado HTTP
    private String mensaje; // mensaje general del error
    private Map<String, String> errores; // Detalla errores por campo 
    private String path; // ruta del endpoint donde ocurrio el error
    
    // CONSTRUCTOR:

    public ErrorDTO(LocalDateTime timestamp, int status, String mensaje, Map<String, String> errores, String path){

        this.timeStamp = timestamp;
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
        this.path = path;

    }

}
