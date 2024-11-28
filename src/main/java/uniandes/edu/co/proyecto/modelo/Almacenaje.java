package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="almacenajes")
public class Almacenaje {

    @EmbeddedId
    private AlmacenajePK pk;

    private Long costo_promedio;

    private Long capacidad;
    private Long cantidad;

    private Long nivel_minimo;

    public Almacenaje(){;}

    public Almacenaje(Long costoPromedio, Long capacidadBodega, Long nivelMinimoReorden, Long cantidad, Bodega bodega, Producto producto){
        this.pk=new AlmacenajePK(bodega, producto);
        this.costo_promedio = costoPromedio;
        this.capacidad = capacidadBodega;
        this.nivel_minimo = nivelMinimoReorden;
        this.cantidad=cantidad;
    }

    

    public Long getCosto_promedio() {
        return costo_promedio;
    }

    public void setCosto_promedio(Long costo_promedio) {
        this.costo_promedio = costo_promedio;
    }

    public Long getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Long capacidad) {
        this.capacidad = capacidad;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getNivel_minimo() {
        return nivel_minimo;
    }

    public void setNivel_minimo(Long nivel_minimo) {
        this.nivel_minimo = nivel_minimo;
    }

    public AlmacenajePK getPk() {
        return pk;
    }

    public void setPk(AlmacenajePK pk) {
        this.pk = pk;
    }

    
}
