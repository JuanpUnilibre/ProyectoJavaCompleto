package com.universidad.dto.facultad;

import com.universidad.modelo.enumeracion.EstadoEntidad;

public record FacultadDto (
        Long id,
        String CodigoFacultad,
        String nombre,
        String descripcion,
        EstadoEntidad estado,
        Boolean activo
        ){
    
}
