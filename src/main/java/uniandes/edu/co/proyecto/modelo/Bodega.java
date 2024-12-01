package uniandes.edu.co.proyecto.modelo;

import lombok.ToString;

@ToString
public class Bodega {

    private int numero;

    private String nombre;

    private int tamaño;

    public Bodega(){}

    public Bodega(String nombre, int tamaño){

        this.tamaño = tamaño;
        this.nombre = nombre;

    }

    public int getNumero() {
        return numero;
    }


    public void setNumero(int id) {
        this.numero = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getTamaño(){
        return tamaño;
    }

    public void setTamaño(int tamaño){
        this.tamaño = tamaño;
    }

}
