package com.universidad.vista;

import com.cleandev.cli.core.SystemModule;
import com.cleandev.cli.io.Console;
import com.cleandev.cli.io.ScreenFormatter;
import com.cleandev.cli.io.TableColumn;
import com.cleandev.cli.io.TableRenderer;
import com.universidad.controlador.ProfesorControlador;
import com.universidad.dto.profesor.ProfesorCrearDto;
import com.universidad.dto.profesor.ProfesorDto;
import java.util.List;

public class ProfesorVista implements SystemModule {

    private final int ANCHO_TABLA;
    private final ProfesorControlador controlador;
    private final List<TableColumn> columnasTablaProfe;

    public ProfesorVista(ProfesorControlador controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("Error controlador");
        }
        this.controlador = controlador;
        this.columnasTablaProfe = List.of(
                new TableColumn("Cod", "%-6s"),
                new TableColumn("Nombre", "%-30s"),
                new TableColumn("Telefono", "%-12s"),
                new TableColumn("Estado", "%-10s")
        );
        ANCHO_TABLA = 75;
    }

    @Override
    public String getModuleName() {
        return "Sistema de profesores";
    }

    @Override
    public void execute(Console cnsl, ScreenFormatter sf, TableRenderer tr) {
        mostrarMenu(cnsl, sf, tr);
    }

    private void mostrarMenu(
            Console console,
            ScreenFormatter formatter,
            TableRenderer renderer) {
        boolean ejecutar = true;
        while (ejecutar) {
            int total = controlador.cantidadProfesores();
            String opcionCrear = "1. Crear profesor";
            String opcionListar = "2. listar profesores " + total;
            console.showMenu(
                    "Sistema de profes",
                    opcionCrear,
                    opcionListar,
                    "0. Regresar menu principal"
            );
            String opcion = console.readText("Deme opcion");
            switch (opcion) {
                case "1" ->
                    crearProfe(console, formatter, renderer);
                case "2" ->
                    listarProfes(console, formatter, renderer);
                case "0" ->
                    ejecutar = false;
                default ->
                    console.showError("Opcion errada");
            }
        }
    }

    private String textoEstado(
            ProfesorDto dto, ScreenFormatter formatter
    ) {
        String descripcion = (dto != null && dto.estado() != null)
                ? dto.estado().getDescription()
                : null;
        return formatter.optionalText(descripcion);
    }

    private Object[] extraerDatosProfesor(ProfesorDto dto,
            ScreenFormatter formatter) {
        return new Object[]{
            String.valueOf(dto.id()),
            dto.nombre(),
            formatter.optionalText(dto.celular()),
            textoEstado(dto, formatter)
        };
    }

    private void listarProfes(
            Console console,
            ScreenFormatter formatter,
            TableRenderer renderer) {
        List<ProfesorDto> lista = controlador.listarProfesores();
        if (lista.isEmpty()) {
            console.showMessage("No hay profes");
            return;
        }
        console.showMenu("\nListado de los profes");
        renderer.render(
                columnasTablaProfe,
                lista,
                profe -> extraerDatosProfesor(profe, formatter),
                ANCHO_TABLA
        );
        console.pause();
    }

    private void crearProfe(Console console,
            ScreenFormatter formatter, TableRenderer renderer) {
        console.showMessage("\nNuevo profesor");
        String nombre = console.readText("Dame el nombre [Enter cancelar]");
        if (nombre == null || nombre.isBlank()) {
            return;
        }
        String celular = console.readText("Dame el celular");
        ProfesorCrearDto dto = new ProfesorCrearDto(nombre, celular);
        ProfesorDto dtoRespuesta = controlador.crearProfesor(dto);

        renderer.renderSingle(
                columnasTablaProfe,
                dto,
                profe -> extraerDatosProfesor(dtoRespuesta, formatter),
                ANCHO_TABLA);
        console.pause();
    }
}
