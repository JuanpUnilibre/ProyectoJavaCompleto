package com.universidad.mapeador;

import com.universidad.dto.estudiante.EstudianteDto;
import com.universidad.modelo.Estudiante;

import java.util.List;

public class EstudianteMapeador
        implements Mapeador<Estudiante, EstudianteDto> {

    @Override
    public EstudianteDto toDto(Estudiante entidad) {
        return new EstudianteDto(
                entidad.getIdEstudiante(),
                entidad.getCodigoEstudiante(),
                entidad.getNombreEstudiante(),
                entidad.getCorreoEstudiante(),
                entidad.getCelularEstudiante(),
                entidad.getDireccionEstudiante(),
                entidad.getEstadoEstudiante(),
                entidad.estaActivo()
        );
    }

    @Override
    public List<EstudianteDto> toDtoList(List<Estudiante> entidades) {
        return entidades.stream()
                .map(this::toDto)
                .toList();
    }
}