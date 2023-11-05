package uniandes.edu.co.proyecto.modelo;

import java.sql.Time;

import jakarta.persistence.*;


@Entity
@Table(name="gimnasios")
@PrimaryKeyJoinColumn(name="idservicio")

public class Gimnasio extends Servicio{
    
    // Atributos
    private Integer capacidad;
    private String maquinas;


    // Constructor
    public Gimnasio(Integer capacidad, String maquinas,
                    Time horarioinicio, Time horariofin, String nombre, Integer costo, String cargado, String existe, Reserva reserva)
    {
        super(horarioinicio, horariofin, nombre, costo, cargado, existe, reserva);
        this.capacidad = capacidad;
        this.maquinas = maquinas;
    }


    public Gimnasio(){;}


    // Getters
    public Integer getCapacidad() {
        return capacidad;
    }

    public String getMaquinas() {
        return maquinas;
    }


    // Setters
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public void setMaquinas(String maquinas) {
        this.maquinas = maquinas;
    }
    
}