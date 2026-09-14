package com.universidad.servicio;

import com.universidad.dto.facultad.FacultadCrearDto;
import com.universidad.dto.facultad.FacultadDto;
import com.universidad.mapeador.FacultadMapeador;
import com.universidad.modelo.Facultad;
import com.universidad.repositorio.FacultadRepositorio;
import java.util.List;

public class FacultadServicio {

    private final FacultadMapeador mapeador;
    private final FacultadRepositorio repositorio;

    public FacultadServicio(
            FacultadMapeador mapeador,
            FacultadRepositorio repositorio) {

        if (repositorio == null) {
            throw new IllegalArgumentException("El repositorio esta vacio");
        }

        if (mapeador == null) {
            throw new IllegalArgumentException("El mapeador esta vacio");
        }

        this.mapeador = mapeador;
        this.repositorio = repositorio;
    }

    public FacultadDto registrarFacultad(FacultadCrearDto dto) {

        Facultad nuevaFacultad = new Facultad(
                dto.codigo(),
                dto.nombre(),
                dto.descripcion()
        );

        Facultad guardada = repositorio.guardar(nuevaFacultad);

        return mapeador.toDto(guardada);
    }

    public List<FacultadDto> obtenerFacultades() {
        return mapeador.toDtoList(
                repositorio.listarTodos()
        );
    }

    public int contarFacultades() {
        return (int) repositorio.contar();
    }
}