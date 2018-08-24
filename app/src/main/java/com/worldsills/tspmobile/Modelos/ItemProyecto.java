package com.worldsills.tspmobile.Modelos;

public class ItemProyecto {

    private String nombre;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemProyecto(String nombre, int codigo) {
        this.nombre = nombre;
        this.id= codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
