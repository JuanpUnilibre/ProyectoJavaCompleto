package com.universidad.servicio;

import com.universidad.dto.estudiante.EstudianteCrearDto;
import com.universidad.dto.estudiante.EstudianteDto;
import com.universidad.mapeador.EstudianteMapeador;
import com.universidad.modelo.Estudiante;
import com.universidad.repositorio.EstudianteRepositorio;

import java.util.List;

public class EstudianteServicio {
    
    private final EstudianteMapeador mapeador;
    private final EstudianteRepositorio repositorio;

    public EstudianteServicio(
            EstudianteMapeador mapeador,
            EstudianteRepositorio repositorio) {

        if (repositorio == null) {
            throw new IllegalArgumentException("Error en el Repositorio");
        }

        if (mapeador == null) {
            throw new IllegalArgumentException("Mapeador vacio");
        }

        this.mapeador = mapeador;
        this.repositorio = repositorio;
    }

    public EstudianteDto registrarEstudiante(EstudianteCrearDto dto) {
        Estudiante nuevoEstudiante = new Estudiante(
                dto.codigo(),
                dto.nombre(),
                dto.correo(),
                dto.celular(),
                dto.direccion()
        );

        Estudiante guardado = repositorio.guardar(nuevoEstudiante);

        return mapeador.toDto(guardado);
    }

    public List<EstudianteDto> obtenerEstudiantes() {
        return mapeador.toDtoList(repositorio.listarTodos());
    }

    public int contarEstudiantes() {
        return (int) repositorio.contar();
    }
}
