package com.universidad.vista;

import com.cleandev.cli.core.SystemModule;
import com.cleandev.cli.io.Console;
import com.cleandev.cli.io.ScreenFormatter;
import com.cleandev.cli.io.TableColumn;
import com.cleandev.cli.io.TableRenderer;
import com.universidad.controlador.EstudianteControlador;
import com.universidad.dto.estudiante.EstudianteCrearDto;
import com.universidad.dto.estudiante.EstudianteDto;
import java.util.List;

public class EstudianteVista implements SystemModule {

    private final int ANCHO_TABLA;
    private final EstudianteControlador controlador;
    private final List<TableColumn> columnasTablaEstudiante;

    public EstudianteVista(EstudianteControlador controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("Error controlador");
        }

        this.controlador = controlador;

        this.columnasTablaEstudiante = List.of(
                new TableColumn("Cod", "%-12s"),
                new TableColumn("Nombre", "%-25s"),
                new TableColumn("Correo", "%-30s"),
                new TableColumn("Telefono", "%-12s"),
                new TableColumn("Direccion", "%-25s"),
                new TableColumn("Estado", "%-10s")
        );

        ANCHO_TABLA = 125;
    }

    @Override
    public String getModuleName() {
        return "Sistema de estudiantes";
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

            int total = controlador.cantidadEstudiantes();

            String opcionCrear = "1. Crear estudiante";
            String opcionListar = "2. listar estudiantes " + total;

            console.showMenu(
                    "Sistema de estudiantes",
                    opcionCrear,
                    opcionListar,
                    "0. Regresar menu principal"
            );

            String opcion = console.readText("Deme opcion");

            switch (opcion) {
                case "1" ->
                    crearEstudiante(console, formatter, renderer);

                case "2" ->
                    listarEstudiantes(console, formatter, renderer);

                case "0" ->
                    ejecutar = false;

                default ->
                    console.showError("Opcion errada");
            }
        }
    }

    private String textoEstado(
            EstudianteDto dto,
            ScreenFormatter formatter) {

        String descripcion = (dto != null && dto.estado() != null)
                ? dto.estado().getDescription()
                : null;

        return formatter.optionalText(descripcion);
    }

    private Object[] extraerDatosEstudiante(
            EstudianteDto dto,
            ScreenFormatter formatter) {

        return new Object[]{
            dto.codigo(),
            dto.nombre(),
            formatter.optionalText(dto.correo()),
            formatter.optionalText(dto.celular()),
            formatter.optionalText(dto.direccion()),
            textoEstado(dto, formatter)
        };
    }

    private void listarEstudiantes(
            Console console,
            ScreenFormatter formatter,
            TableRenderer renderer) {

        List<EstudianteDto> lista =
                controlador.listarEstudiantes();

        if (lista.isEmpty()) {
            console.showMessage("No hay estudiantes");
            return;
        }

        console.showMenu("\nListado de los estudiantes");

        renderer.render(
                columnasTablaEstudiante,
                lista,
                estudiante -> extraerDatosEstudiante(
                        estudiante,
                        formatter
                ),
                ANCHO_TABLA
        );

        console.pause();
    }

    private void crearEstudiante(
            Console console,
            ScreenFormatter formatter,
            TableRenderer renderer) {

        console.showMessage("\nNuevo estudiante");

        String codigo = console.readText(
                "Dame el codigo [Enter cancelar]"
        );

        if (codigo == null || codigo.isBlank()) {
            return;
        }

        String nombre = console.readText("Dame el nombre");
        String correo = console.readText("Dame el correo");
        String celular = console.readText("Dame el celular");
        String direccion = console.readText("Dame la direccion");

        EstudianteCrearDto dto = new EstudianteCrearDto(
                codigo,
                nombre,
                correo,
                celular,
                direccion
        );

        EstudianteDto dtoRespuesta =
                controlador.crearEstudiante(dto);

        renderer.renderSingle(
                columnasTablaEstudiante,
                dtoRespuesta,
                estudiante -> extraerDatosEstudiante(
                        dtoRespuesta,
                        formatter
                ),
                ANCHO_TABLA
        );

        console.pause();
    }
}