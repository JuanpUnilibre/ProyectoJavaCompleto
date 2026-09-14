package com.universidad.config;

import com.cleandev.cli.core.SystemModule;
import com.universidad.config.modulos.ConfiguracionModuloEstudiante;
import com.universidad.config.modulos.ConfiguracionModuloProfesor;
import com.universidad.config.modulos.ConfiguracionModuloFacultad;
import com.universidad.config.modulos.ModuloConfigurable;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionDependencia implements AutoCloseable {

    private final List<SystemModule> modulos = new ArrayList<>();

    private final List<ModuloConfigurable> modulosConfigurados
            = new ArrayList<>();

    public ConfiguracionDependencia() {
        ConfiguracionModuloProfesor confiProfe
                = new ConfiguracionModuloProfesor();

        modulosConfigurados.add(confiProfe);
        modulos.add(confiProfe.construirVista());
        //****************************************
        ConfiguracionModuloEstudiante confiEstudiante
                = new ConfiguracionModuloEstudiante();

        modulosConfigurados.add(confiEstudiante);
        modulos.add(confiEstudiante.construirVista());
        //****************************************
        ConfiguracionModuloFacultad confiFacultad
                = new ConfiguracionModuloFacultad();

        modulosConfigurados.add(confiFacultad);
        modulos.add(confiFacultad.construirVista());
    }

    public List<SystemModule> getModulos() {
        return modulos;
    }

    @Override
    public void close() throws Exception {
        for (int i = modulosConfigurados.size() - 1; i >= 0; i--) {
            ModuloConfigurable modulito = modulosConfigurados.get(i);
            modulito.cerrarRecursos();
        }

        modulosConfigurados.clear();
    }
}
