package uniandes.edu.co.proyecto.modelo;

import org.springframework.data.annotation.Id;

import lombok.ToString;

@ToString
public class Bodega {

    @Id
    private int id;

    private String nombre;

    private int tamaño;

    public Bodega(){}

    public Bodega(String nombre, int tamaño){

        this.tamaño = tamaño;
        this.nombre = nombre;

    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
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
