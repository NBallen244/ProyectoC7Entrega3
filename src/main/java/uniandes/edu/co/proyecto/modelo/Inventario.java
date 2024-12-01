package uniandes.edu.co.proyecto.modelo;

import lombok.ToString;

@ToString
public class Inventario {

    private String producto;
    private int costo_promedio;

    private int capacidad;
    private int cantidad;

    private int nivel_minimo;

    public Inventario(){;}

    public Inventario(int costoPromedio, int capacidadBodega, int nivelMinimoReorden, int cantidad, String producto){
        this.producto = producto;
        this.costo_promedio = costoPromedio;
        this.capacidad = capacidadBodega;
        this.nivel_minimo = nivelMinimoReorden;
        this.cantidad=cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCosto_promedio() {
        return costo_promedio;
    }

    public void setCosto_promedio(int costo_promedio) {
        this.costo_promedio = costo_promedio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNivel_minimo() {
        return nivel_minimo;
    }

    public void setNivel_minimo(int nivel_minimo) {
        this.nivel_minimo = nivel_minimo;
    }

    
}
