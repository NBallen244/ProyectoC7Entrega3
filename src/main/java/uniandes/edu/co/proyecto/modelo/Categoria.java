package uniandes.edu.co.proyecto.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;

@Document(collection = "categorias")
@ToString
public class Categoria {

    @Id
    private int codigo;

    private String nombre;

    private String descripcion;

    private String caracteristicas;

    public Categoria(String nombre, String descripcion, String caracteristicas){

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;

    }

    public Categoria(){}

    public int getCodigo() {
        return codigo;
    }

    public void setcodigo(int codigo) {
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
