package com.universidad.config.modulos;

import com.cleandev.cli.core.SystemModule;
import com.cleandev.tpa.api.TpaRepository;
import com.cleandev.tpa.api.TpaRepositoryFactory;
import com.universidad.config.RutaPersistencia;
import com.universidad.controlador.FacultadControlador;
import com.universidad.mapeador.FacultadMapeador;
import com.universidad.modelo.Facultad;
import com.universidad.persistencia.FacultadRepositorioImpl;
import com.universidad.repositorio.FacultadRepositorio;
import com.universidad.servicio.FacultadServicio;
import com.universidad.vista.FacultadVista;

public class ConfiguracionModuloFacultad implements ModuloConfigurable {

    private final FacultadRepositorio repositorio;
    private final FacultadServicio servicio;

    public ConfiguracionModuloFacultad() {

        TpaRepository<Facultad, Long> tpaEngine =
                TpaRepositoryFactory.create(
                        Facultad.class,
                        RutaPersistencia.FACULTADES.obtenerRuta(),
                        false //No tenemos UUID
                );

        FacultadMapeador mapeador = new FacultadMapeador();

        repositorio = new FacultadRepositorioImpl(tpaEngine);

        servicio = new FacultadServicio(
                mapeador,
                repositorio
        );
    }

    public FacultadRepositorio getRepositorio() {
        return repositorio;
    }

    public FacultadServicio getServicio() {
        return servicio;
    }

    @Override
    public SystemModule construirVista() {

        FacultadControlador controlador =
                new FacultadControlador(servicio);

        return new FacultadVista(controlador);
    }

    @Override
    public void cerrarRecursos() {

        if (repositorio != null) {
            repositorio.cerrar();
        }
    }
}