package uniandes.edu.co.proyecto.modelo;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.ToString;


@Document(collection = "productos")
@ToString
public class Producto {

    @Transient
    public static final String SEQUENCE_NAME = "secuencia_productos";

    @Id
    private int cod_barras;

    private String nombre;

    private int costo_bodega;

    private int precio_unitario;

    private String presentacion;

    private int cantidad_presentacion;

    private String unidad_medida;

    private int peso;

    private int volumen;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    private Date fecha_vencimiento;

    private int categoria;

    public Producto(){;}

    public Producto(int cod_barras, String nombre, int costoBodega, int precioUnitario, String presentacion, int cantidadPresentacion, String unidadMedida, int peso, int volumen, Date fecha_vencimiento, int categoria){
        
        this.cod_barras = cod_barras;
        this.nombre = nombre;
        this.costo_bodega = costoBodega;
        this.precio_unitario = precioUnitario;
        this.presentacion = presentacion;
        this.peso = peso;
        this.volumen = volumen;
        this.unidad_medida = unidadMedida;
        this.cantidad_presentacion = cantidadPresentacion;
        this.fecha_vencimiento = fecha_vencimiento;
        this.categoria = categoria;

    }

    //GETTERS

    public int getCod_barras(){
        return cod_barras;
    }

    public String getNombre(){
        return nombre;
    }

    public int getCosto_bodega(){
        return costo_bodega;
    }

    public int getPrecio_unitario(){
        return precio_unitario;
    }

    public String getPresentacion(){
        return presentacion;
    }

    public int getPeso(){
        return peso;
    }

    public int getVolumen(){
        return volumen;
    }

    public String getUnidad_medida(){
        return unidad_medida;
    }

    public int getCantidad_presentacion(){
        return cantidad_presentacion;
    }

    public Date getFecha_vencimiento(){
        return fecha_vencimiento;
    }

    public int getCategoria(){
        return categoria;
    }

    //SETTERS

    public void setCodigoBarras(int cod_barras){
        this.cod_barras = cod_barras;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setCostoBodega(int costo_bodega){
        this.costo_bodega = costo_bodega;
    }

    public void setPrecioUnitario(int precio_unitario){
        this.precio_unitario = precio_unitario;
    }

    public void setPresentacion(String presentacion){
        this.presentacion = presentacion;
    }

    public void setPeso(int peso){
        this.peso = peso;
    }

    public void setVolumen(int volumen){
        this.volumen = volumen;
    }

    public void setUnidadMedida(String unidad_medida){
        this.unidad_medida = unidad_medida;
    }

    public void setCantidadPresentacion(int cantidad_presentacion){
        this.cantidad_presentacion = cantidad_presentacion;
    }

    public void setFechaVencimiento(Date fecha_vencimiento){
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public void setCategoria(int categoria){
        this.categoria = categoria;
    }


}
