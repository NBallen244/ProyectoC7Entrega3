package uniandes.edu.co.proyecto.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;

@Document(collection = "proveedores")
@ToString
public class Proveedor {

    @Id
    private int nit;

    private String nombre;

    private String direccion;

    private String contacto;

    private int tel_contacto;

    private List<Integer> productos;

    public Proveedor(){}
    
    public Proveedor(int nit, String nombre, String direccion, String contacto, int tel_contacto, List<Integer> productos) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.contacto = contacto;
        this.tel_contacto = tel_contacto;
        this.productos = productos;
    }


    public int getNit() {
        return nit;
    }


    public void setNit(int nit) {
        this.nit = nit;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getContacto() {
        return contacto;
    }


    public void setContacto(String contacto) {
        this.contacto = contacto;
    }


    public int getTel_contacto() {
        return tel_contacto;
    }


    public void setTel_contacto(int tel_contacto) {
        this.tel_contacto = tel_contacto;
    }


    public List<Integer> getProductos() {
        return productos;
    }


    public void setProductos(List<Integer> productos) {
        this.productos = productos;
    }

    

}