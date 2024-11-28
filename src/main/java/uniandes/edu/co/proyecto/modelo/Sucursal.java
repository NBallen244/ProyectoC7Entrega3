package uniandes.edu.co.proyecto.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;

@Document(collection = "sucursales")
@ToString
public class Sucursal {

    @Id
    private Long id;

    private String nombre;

    private Long tamaño;

    private String direccion;

    private Long telefono;

    private String ciudad;

    private List<Bodega> bodegas;

    public Sucursal(String nombre, Long tamaño, String direccion, Long telefono, String ciudad){
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTamaño() {
        return tamaño;
    }

    public void setTamaño(Long tamaño) {
        this.tamaño = tamaño;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    //TODO: GETTER Y SETTERS

    
    
}
