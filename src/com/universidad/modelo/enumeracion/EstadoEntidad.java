package com.universidad.modelo.enumeracion;

import com.cleandev.tpa.api.converter.CodedEnum;

public enum EstadoEntidad implements CodedEnum{
    ACTIVO(1, "Activo"),
    INACTIVO(2, "Inactivo");
    
    private final int code;
    private final String destription;

    private EstadoEntidad(int code, String destription) {
        this.code = code;
        this.destription = destription;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return destription;
    }
    
    //maquina de estados
    public EstadoEntidad cambiarEstadoA (EstadoEntidad nuevoEstado){
        if(this == ACTIVO && nuevoEstado==INACTIVO){
            return nuevoEstado;
        }
        if(this ==INACTIVO && nuevoEstado==ACTIVO){
            return nuevoEstado;
        }
        throw new IllegalStateException("Error estado "
                + this + " igual al nuevo " + nuevoEstado);
    }
    
    
}
