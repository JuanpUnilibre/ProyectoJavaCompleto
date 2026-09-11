package com.universidad.repositorio.cargador;

import com.universidad.modelo.Estudiante;
import com.universidad.repositorio.EstudianteRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EstudianteCargador
        implements CargadorDatos<Estudiante, UUID> {

    private final Map<UUID, Estudiante> cache;
    private final EstudianteRepositorio repositorio;

    public EstudianteCargador(EstudianteRepositorio repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("Error de repositorio");
        }

        this.repositorio = repositorio;
        this.cache = new HashMap<>();
    }

    @Override
    public void cargarTodos() {
        this.cache.clear();

        for (Estudiante estudiante : repositorio.listarTodos()) {
            cache.put(estudiante.getIdEstudiante(), estudiante);
        }
    }

    @Override
    public void cargarPorIds(List<UUID> ids) {
        this.cache.clear();

        if (ids == null) {
            return;
        }

        for (UUID id : ids) {
            repositorio.buscarPorId(id)
                    .ifPresent(estudiante ->
                            cache.put(estudiante.getIdEstudiante(), estudiante));
        }
    }

    @Override
    public void registrarEnCache(Estudiante entidad) {
        if (entidad != null && entidad.getIdEstudiante() != null) {
            cache.put(entidad.getIdEstudiante(), entidad);
        }
    }

    @Override
    public Optional<Estudiante> obtener(UUID id) {
        if (id == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public boolean existe(UUID id) {
        return id != null && cache.containsKey(id);
    }
}