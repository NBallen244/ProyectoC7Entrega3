package uniandes.edu.co.proyecto.modelo;


import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.ToString;


@Document(collection = "ordenes")
@ToString
public class Orden {

    @Transient
    public static final String SEQUENCE_NAME = "secuencia_ordenes";

    @Id
    private int id;

    private String estado="vigente";

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    private Date fecha_estimada;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    private Date fecha_creacion;

    private int proveedor;

    private int sucursal_destino;

    private List<ProductosOrden> productos;

    public Orden(){;}

    public Orden(int id, Date fecha_estimada, int proveedor, int sucursal_destino){
        this.id = id;
        this.fecha_creacion = new Date();
        this.fecha_estimada = fecha_estimada;
        this.proveedor = proveedor;
        this.sucursal_destino = sucursal_destino;  
    }

    //GETTERS

    public int getId() {
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

    public int getProveedor(){
        return proveedor;
    }

    public int getSucursal_destino(){
        return sucursal_destino;
    }

    public List<ProductosOrden> getProductos() {
        return productos;
    }

    //SETTERS

    

    public void setId(int id) {
        this.id = id;
    }

    public void setProductos(List<ProductosOrden> productos) {
        this.productos = productos;
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

    public void setProveedor(int proveedor){
        this.proveedor = proveedor;
    }

    public void setSucursal_destino(int sucursal_destino) {
        this.sucursal_destino = sucursal_destino;
    }

}
