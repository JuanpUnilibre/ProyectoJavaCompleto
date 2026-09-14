package com.universidad.repositorio.cargador;

import com.universidad.modelo.Facultad;
import com.universidad.repositorio.FacultadRepositorio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacultadCargador implements CargadorDatos<Facultad, Long> {

    private final FacultadRepositorio repositorio;
    private final Map<Long, Facultad> cache = new HashMap<>();

    public FacultadCargador(FacultadRepositorio repositorio) {

        if (repositorio == null) {
            throw new IllegalArgumentException("El repositorio esta vacio");
        }

        this.repositorio = repositorio;
    }

    @Override
    public void cargarTodos() {

        List<Facultad> facultades = repositorio.listarTodos();

        cache.clear();

        for (Facultad facultad : facultades) {
            registrarEnCache(facultad);
        }
    }

    @Override
    public void cargarPorIds(List<Long> ids) {

        if (ids == null) {
            throw new IllegalArgumentException("La lista de ids no puede ser nula");
        }

        for (Long id : ids) {

            Optional<Facultad> facultad =
                    repositorio.buscarPorId(id);

            facultad.ifPresent(this::registrarEnCache);
        }
    }

    @Override
    public void registrarEnCache(Facultad entidad) {

        if (entidad == null) {
            throw new IllegalArgumentException(
                    "La facultad no puede ser nula");
        }

        cache.put(entidad.getIdFacultad(), entidad);
    }

    @Override
    public Optional<Facultad> obtener(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public boolean existe(Long id) {

        if (id == null) {
            return false;
        }

        return cache.containsKey(id);
    }
}