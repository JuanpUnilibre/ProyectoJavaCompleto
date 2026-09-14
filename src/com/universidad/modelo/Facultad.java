package com.universidad.modelo;

import com.cleandev.tpa.api.annotations.TpaConvert;
import com.cleandev.tpa.api.annotations.TpaId;
import com.universidad.modelo.convertidor.EstadoEntidadConverter;
import com.universidad.modelo.enumeracion.EstadoEntidad;

public class Facultad {

    @TpaId
    private Long idFacultad;
    private String codigoFacultad;
    private String nombreFacultad;
    private String descripcionFacultad;

    @TpaConvert(converter = EstadoEntidadConverter.class)
    private EstadoEntidad estadoFacultad;

    //Reflexion
    protected Facultad() {
    }

    //Creacion
    public Facultad(
            String codigoFacultad,
            String nombreFacultad,
            String descripcionFacultad) {
        this.codigoFacultad = codigoFacultad;
        this.nombreFacultad = nombreFacultad;
        this.descripcionFacultad = descripcionFacultad;
        this.estadoFacultad = EstadoEntidad.ACTIVO;
    }

    //Hidratacion
    public Facultad(
            Long idFacultad,
            String codigoFacultad,
            String nombreFacultad,
            String descripcionFacultad,
            EstadoEntidad estadoFacultad) {

        this.codigoFacultad = codigoFacultad;
        this.nombreFacultad = nombreFacultad;
        this.descripcionFacultad = descripcionFacultad;
        this.estadoFacultad = estadoFacultad;

        if (idFacultad == null) {
            throw new IllegalArgumentException("El id de la facultad es obligatorio en Hidratacion");
        }

        this.idFacultad = idFacultad;
    }

    //Metodos de comportamiento
    public void actualizarNombre(String nuevoNombre) {
        if (nuevoNombre.equalsIgnoreCase(this.nombreFacultad)) {
            throw new IllegalArgumentException("Los nombres son iguales");
        }
        this.nombreFacultad = nuevoNombre;
    }

    public void cambiarEstado(EstadoEntidad nuevoEstado) {
        this.estadoFacultad = estadoFacultad.cambiarEstadoA(nuevoEstado);
    }

    //Metodos de consulta
    public boolean estaActivo() {
        return this.estadoFacultad == EstadoEntidad.ACTIVO;
    }

    //Getters
    public Long getIdFacultad() {
        return idFacultad;
    }

    public String getCodigoFacultad() {
        return codigoFacultad;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public String getDescripcionFacultad() {
        return descripcionFacultad;
    }

    public EstadoEntidad getEstadoFacultad() {
        return estadoFacultad;
    }
}
