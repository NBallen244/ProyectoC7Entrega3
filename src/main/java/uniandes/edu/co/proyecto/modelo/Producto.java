package uniandes.edu.co.proyecto.modelo;

import java.sql.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="productos")
public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long cod_barras;

    private String nombre;

    private Long costo_bodega;

    private Long precio_unitario;

    private String presentacion;

    private Long cantidad_presentacion;

    private String unidad_medida;

    private Long peso;

    private Long volumen;

    private Date fecha_vencimiento;

    @ManyToOne
    @JoinColumn(name = "categoria", referencedColumnName = "codigo")
    private Categoria categoria;

    public Producto(){;}

    public Producto(Long cod_barras, String nombre, Long costoBodega, Long precioUnitario, String presentacion, Long cantidadPresentacion, String unidadMedida, Long peso, Long volumen, Date fecha_vencimiento, Categoria categoria){
        
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

    public Long getCod_barras(){
        return cod_barras;
    }

    public String getNombre(){
        return nombre;
    }

    public Long getCosto_bodega(){
        return costo_bodega;
    }

    public Long getPrecio_unitario(){
        return precio_unitario;
    }

    public String getPresentacion(){
        return presentacion;
    }

    public Long getPeso(){
        return peso;
    }

    public Long getVolumen(){
        return volumen;
    }

    public String getUnidad_medida(){
        return unidad_medida;
    }

    public Long getCantidad_presentacion(){
        return cantidad_presentacion;
    }

    public Date getFecha_vencimiento(){
        return fecha_vencimiento;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    //SETTERS

    public void setCodigoBarras(Long cod_barras){
        this.cod_barras = cod_barras;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setCostoBodega(Long costo_bodega){
        this.costo_bodega = costo_bodega;
    }

    public void setPrecioUnitario(Long precio_unitario){
        this.precio_unitario = precio_unitario;
    }

    public void setPresentacion(String presentacion){
        this.presentacion = presentacion;
    }

    public void setPeso(Long peso){
        this.peso = peso;
    }

    public void setVolumen(Long volumen){
        this.volumen = volumen;
    }

    public void setUnidadMedida(String unidad_medida){
        this.unidad_medida = unidad_medida;
    }

    public void setCantidadPresentacion(Long cantidad_presentacion){
        this.cantidad_presentacion = cantidad_presentacion;
    }

    public void setFechaVencimiento(Date fecha_vencimiento){
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }


}
