package uniandes.edu.co.proyecto.modelo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;

@Document(collection = "almacenajes")
@ToString
public class Almacenaje {

    private int sucursal;
    private int bodega;

    private List<Inventario> inventarios;

    public Almacenaje(){;}

    public Almacenaje(int bodega, int sucursal){
        this.bodega = bodega;
        this.sucursal = sucursal;   
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public int getBodega() {
        return bodega;
    }

    public void setBodega(int bodega) {
        this.bodega = bodega;
    }

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    
    
}
