package com.worldsills.tspmobile.Modelos;

public class ItemInformation {
    private double porcentaje;
    private String phase;

    public ItemInformation(double porcentaje, String phase) {
        this.porcentaje = porcentaje;
        this.phase = phase;
    }


    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
