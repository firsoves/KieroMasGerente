package com.magic.kieromasgerente.Datos;

public class _DatosIngridientesElegibles {
    int idIngridienteElegible;
    public String nombreIngridienteElegible;

    public _DatosIngridientesElegibles(int idIngridienteElegible, String nombreIngridienteElegible) {
        this.idIngridienteElegible = idIngridienteElegible;
        this.nombreIngridienteElegible = nombreIngridienteElegible;
    }

    public int getIdIngridienteElegible() {
        return idIngridienteElegible;
    }

    public void setIdIngridienteElegible(int idIngridienteElegible) {
        this.idIngridienteElegible = idIngridienteElegible;
    }

    public String getNombreIngridienteElegible() {
        return nombreIngridienteElegible;
    }

    public void setNombreIngridienteElegible(String nombreIngridienteElegible) {
        this.nombreIngridienteElegible = nombreIngridienteElegible;
    }
}
