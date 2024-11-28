package uniandes.edu.co.proyecto.modelo;

import org.springframework.data.annotation.Id;

import lombok.ToString;

@ToString
public class Bodega {

    @Id
    private Long id;

    private String nombre;

    private Long tamaño;


    public Bodega(String nombre, Long tamaño){

        this.tamaño = tamaño;
        this.nombre = nombre;

    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Long getTamaño(){
        return tamaño;
    }

    public void setTamaño(Long tamaño){
        this.tamaño = tamaño;
    }

}
