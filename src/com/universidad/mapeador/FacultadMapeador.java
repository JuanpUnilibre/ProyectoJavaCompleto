package com.universidad.mapeador;

import com.universidad.dto.facultad.FacultadDto;
import com.universidad.modelo.Facultad;
import java.util.List;

public class FacultadMapeador implements Mapeador<Facultad, FacultadDto> {

    @Override
    public FacultadDto toDto(Facultad entidad) {

        if (entidad == null) {
            throw new IllegalArgumentException("La facultad no puede ser nula");
        }

        return new FacultadDto(
                entidad.getIdFacultad(),
                entidad.getCodigoFacultad(),
                entidad.getNombreFacultad(),
                entidad.getDescripcionFacultad(),
                entidad.getEstadoFacultad(),
                entidad.estaActivo()
        );
    }

    @Override
    public List<FacultadDto> toDtoList(List<Facultad> entidades) {

        if (entidades == null) {
            throw new IllegalArgumentException(
                    "La lista de facultades no puede ser nula");
        }

        return entidades.stream()
                .map(this::toDto)
                .toList();
    }
}