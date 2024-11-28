package uniandes.edu.co.proyecto.modelo;

import lombok.ToString;

@ToString
public class ProductosOrden {

    private int producto;
    private int cantidad;
    private int precio_acordado;

    public ProductosOrden(){;}
    
    public ProductosOrden(int producto, int cantidad, int precio_acordado) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_acordado = precio_acordado;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio_acordado() {
        return precio_acordado;
    }

    public void setPrecio_acordado(int precio_acordado) {
        this.precio_acordado = precio_acordado;
    }
    


}
