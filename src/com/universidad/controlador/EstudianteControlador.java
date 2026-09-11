
package com.universidad.controlador;

import com.universidad.dto.estudiante.EstudianteCrearDto;
import com.universidad.dto.estudiante.EstudianteDto;
import com.universidad.servicio.EstudianteServicio;
import java.util.List;

public class EstudianteControlador {
    private final EstudianteServicio servicio;

    public EstudianteControlador(EstudianteServicio servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("Servicio vacio");
        }

        this.servicio = servicio;
    }

    public EstudianteDto crearEstudiante(EstudianteCrearDto dto) {
        return servicio.registrarEstudiante(dto);
    }

    public int cantidadEstudiantes() {
        return servicio.contarEstudiantes();
    }

    public List<EstudianteDto> listarEstudiantes() {
        return servicio.obtenerEstudiantes();
    }
}
