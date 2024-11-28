package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long codigo;

    private String nombre;

    private String descripcion;

    private String caracteristicas;

    public Categoria(){;}

    public Categoria(String nombre, String descripcion, String caracteristicas){

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;

    }

    public Long getCodigo() {
        return codigo;
    }

    public void setcodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas){
        this.caracteristicas = caracteristicas;
    }
}
