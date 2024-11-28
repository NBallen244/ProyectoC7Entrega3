package uniandes.edu.co.proyecto.modelo;


import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;


@Document(collection = "productos")
@ToString
public class Orden {

    @Id
    private int id;

    private String estado="vigente";

    private Date fecha_estimada;

    private Date fecha_creacion=new Date();

    private int proveedor;

    private int sucursal_destino;

    private List<ProductosOrden> productos;

    public Orden(){;}

    public Orden(Date fecha_estimada, int proveedor, int sucursal_destino){
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
