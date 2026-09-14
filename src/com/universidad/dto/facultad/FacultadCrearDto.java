package com.universidad.dto.facultad;

import com.universidad.dto.validacion.ReglasValidacion;

public record FacultadCrearDto(
        String codigo,
        String nombre,
        String descripcion) {

    public FacultadCrearDto {
        codigo = ReglasValidacion.limpiarRequerido(
                codigo,
                "Codigo es obligatorio");

        nombre = ReglasValidacion.limpiarRequerido(
                nombre,
                "Nombre es obligatorio");

        descripcion = ReglasValidacion.limpiarRequerido(
                descripcion,
                "Descripcion es obligatoria");
    }
}