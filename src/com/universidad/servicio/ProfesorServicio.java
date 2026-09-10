package com.universidad.servicio;

import com.universidad.dto.profesor.ProfesorCrearDto;
import com.universidad.dto.profesor.ProfesorDto;
import com.universidad.mapeador.ProfesorMapeador;
import com.universidad.modelo.Profesor;
import com.universidad.repositorio.ProfesorRepositorio;
import java.util.List;

public class ProfesorServicio {

    private final ProfesorMapeador mapeador;
    private final ProfesorRepositorio repositorio;

    public ProfesorServicio(ProfesorMapeador mapeador, ProfesorRepositorio repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("pailas con el repo");
        }
        if (mapeador == null) {
            throw new IllegalArgumentException("Mapeador vacio");
        }

        this.mapeador = mapeador;
        this.repositorio = repositorio;
    }

    public ProfesorDto registrarProfesor(ProfesorCrearDto dto) {
        Profesor nuevoProfesor = new Profesor(dto.nombre(), dto.celular());
        Profesor guardado = repositorio.guardar(nuevoProfesor);
        return mapeador.toDto(guardado);
    }

    public List<ProfesorDto> obtenerProfesores() {
        return mapeador.toDtoList(repositorio.listarTodos());
    }

    public int contarProfesores() {
        return (int) repositorio.contar();
    }
}
