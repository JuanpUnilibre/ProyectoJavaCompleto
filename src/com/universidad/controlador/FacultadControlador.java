package com.universidad.controlador;

import com.universidad.dto.facultad.FacultadCrearDto;
import com.universidad.dto.facultad.FacultadDto;
import com.universidad.servicio.FacultadServicio;
import java.util.List;

public class FacultadControlador {

    private final FacultadServicio servicio;

    public FacultadControlador(FacultadServicio servicio) {

        if (servicio == null) {
            throw new IllegalArgumentException("El servicio esta vacio");
        }

        this.servicio = servicio;
    }

    public FacultadDto crearFacultad(FacultadCrearDto dto) {
        return servicio.registrarFacultad(dto);
    }

    public int cantidadFacultades() {
        return servicio.contarFacultades();
    }

    public List<FacultadDto> listarFacultades() {
        return servicio.obtenerFacultades();
    }
}