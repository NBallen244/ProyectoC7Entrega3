package uniandes.edu.co.proyecto.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;

@Document(collection = "inventarios")
@ToString
public class Almacenaje {

    @Id
    private int id;
    private int sucursal;
    private int bodega;

    private List<Inventario> inventarios;

    public Almacenaje(){;}

    public Almacenaje(int id, int bodega, int sucursal){
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}
