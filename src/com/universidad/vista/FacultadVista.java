package com.universidad.vista;

import com.cleandev.cli.core.SystemModule;
import com.cleandev.cli.io.Console;
import com.cleandev.cli.io.ScreenFormatter;
import com.cleandev.cli.io.TableColumn;
import com.cleandev.cli.io.TableRenderer;
import com.universidad.controlador.FacultadControlador;
import com.universidad.dto.facultad.FacultadCrearDto;
import com.universidad.dto.facultad.FacultadDto;
import java.util.List;

public class FacultadVista implements SystemModule {

    private final int ANCHO_TABLA;
    private final FacultadControlador controlador;
    private final List<TableColumn> columnasTablaFacultad;

    public FacultadVista(FacultadControlador controlador) {

        if (controlador == null) {
            throw new IllegalArgumentException("Error controlador");
        }

        this.controlador = controlador;

        this.columnasTablaFacultad = List.of(
                new TableColumn("Cod", "%-10s"),
                new TableColumn("Nombre", "%-30s"),
                new TableColumn("Descripcion", "%-40s"),
                new TableColumn("Estado", "%-10s")
        );

        ANCHO_TABLA = 100;
    }

    @Override
    public String getModuleName() {
        return "Sistema de facultades";
    }

    @Override
    public void execute(
            Console cnsl,
            ScreenFormatter sf,
            TableRenderer tr) {

        mostrarMenu(cnsl, sf, tr);
    }

    private void mostrarMenu(
            Console console,
            ScreenFormatter formatter,
            TableRenderer renderer) {

        boolean ejecutar = true;

        while (ejecutar) {

            int total = controlador.cantidadFacultades();

            String opcionCrear = "1. Crear facultad";
            String opcionListar = "2. Listar facultades " + total;

            console.showMenu(
                    "Sistema de facultades",
                    opcionCrear,
                    opcionListar,
                    "0. Regresar menu principal"
            );

            String opcion = console.readText("Deme opcion");

            switch (opcion) {

                case "1" ->
                    crearFacultad(console, formatter, renderer);

                case "2" ->
                    listarFacultades(console, formatter, renderer);

                case "0" ->
                    ejecutar = false;

                default ->
                    console.showError("Opcion errada");
            }
        }
    }

    private String textoEstado(
            FacultadDto dto,
            ScreenFormatter formatter) {

        String descripcion =
                (dto != null && dto.estado() != null)
                        ? dto.estado().getDescription()
                        : null;

        return formatter.optionalText(descripcion);
    }

    private Object[] extraerDatosFacultad(
        FacultadDto dto,
        ScreenFormatter formatter) {

    return new Object[]{
        dto.CodigoFacultad(),
        dto.nombre(),
        formatter.optionalText(dto.descripcion()),
        textoEstado(dto, formatter)
    };
}

    private void listarFacultades(
            Console console,
            ScreenFormatter formatter,
            TableRenderer renderer) {

        List<FacultadDto> lista =
                controlador.listarFacultades();

        if (lista.isEmpty()) {
            console.showMessage("No hay facultades");
            return;
        }

        console.showMenu("\nListado de facultades");

        renderer.render(
                columnasTablaFacultad,
                lista,
                facultad -> extraerDatosFacultad(
                        facultad,
                        formatter
                ),
                ANCHO_TABLA
        );

        console.pause();
    }

    private void crearFacultad(
            Console console,
            ScreenFormatter formatter,
            TableRenderer renderer) {

        console.showMessage("\nNueva facultad");

        String codigo =
                console.readText(
                        "Dame el codigo [Enter cancelar]"
                );

        if (codigo == null || codigo.isBlank()) {
            return;
        }

        String nombre =
                console.readText("Dame el nombre");

        String descripcion =
                console.readText("Dame la descripcion");

        FacultadCrearDto dto =
                new FacultadCrearDto(
                        codigo,
                        nombre,
                        descripcion
                );

        FacultadDto dtoRespuesta =
                controlador.crearFacultad(dto);

        renderer.renderSingle(
                columnasTablaFacultad,
                dtoRespuesta,
                facultad -> extraerDatosFacultad(
                        dtoRespuesta,
                        formatter
                ),
                ANCHO_TABLA
        );

        console.pause();
    }
}