package uniandes.edu.co.proyecto.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Date;


@Entity
@Table(name="ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long id;

    private String estado;

    private Date fecha_estimada;

    private Date fecha_creacion;

    @ManyToOne
    @JoinColumn(name = "proveedor", referencedColumnName = "nit")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "sucursal_destino", referencedColumnName = "id")
    private Sucursal sucursal_destino;

    public Orden(){;}

    public Orden(Date fecha_estimada, Proveedor proveedor, Sucursal sucursal_destino){
        this.estado = "vigente";
        this.fecha_estimada = fecha_estimada;
        this.fecha_creacion = new Date(new java.util.Date().getTime());
        this.proveedor = proveedor;
        this.sucursal_destino = sucursal_destino;
        
    }

    //GETTERS

    public Long getId() {
        return id;
    }

    public String getEstado(){
        return estado;
    }

    public Date getFecha_estimada(){
        return fecha_estimada;
    }

    public Date getFecha_creacion(){
        return fecha_creacion;
    }

    public Proveedor getProveedor(){
        return proveedor;
    }

    public Sucursal getSucursal_destino(){
        return sucursal_destino;
    }

    //SETTERS

    public void setId(Long id) {
        this.id = id;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public void setFechaEstimada(Date fecha_estimada){
        this.fecha_estimada = fecha_estimada;
    }

    public void setFechaCreacion(Date fecha_creacion){
        this.fecha_creacion = fecha_creacion;
    }

    public void setProveedor(Proveedor proveedor){
        this.proveedor = proveedor;
    }

}
