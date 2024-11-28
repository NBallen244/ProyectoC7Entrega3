package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="proveedores")
public class Proveedor {

    @Id
    private Long nit;

    private String nombre;

    private String direccion;

    private String contacto;

    private Long tel_contacto;

    public Proveedor(){;}

    public Proveedor(Long nit, String nombre, String direccion, String contacto, Long tel_contacto){
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.contacto = contacto;
        this.tel_contacto = tel_contacto;
    }

    //TODO: GETTERS Y SETTERS

    public Long getNIT() {
        return nit;
    }

    public void setNIT(Long nit) {
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

    public Long getTel_contacto() {
        return tel_contacto;
    }

    public void setTelefono(Long tel_contacto) {
        this.tel_contacto = tel_contacto;
    }

}
