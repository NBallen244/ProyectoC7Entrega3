package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")

public class Usuario { 

    // Atributos
    @Id //PK
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer iduser;

    private String nombreuser;
    private String tipodocuser;
    private Integer numdocuser;
    private String correouser;
<<<<<<< HEAD
=======
    // //FK
    // @ManyToOne 
    // @JoinColumn(name="alojamientos", referencedColumnName = "idalojamiento")
    // private Alojamiento alojamiento;
>>>>>>> 6ee3f843aee9efa79a8dbc49bbd1e4bdb4485a2a


    // Constructor
    public Usuario(String nombreuser, String tipodocuser, Integer numdocuser, String correouser)
    {
        this.nombreuser = nombreuser;
        this.tipodocuser = tipodocuser;
        this.numdocuser = numdocuser;
        this.correouser = correouser;
<<<<<<< HEAD
=======
        // this.alojamiento = alojamiento;
>>>>>>> 6ee3f843aee9efa79a8dbc49bbd1e4bdb4485a2a
    }


    public Usuario(){;}


    // Getters
    public Integer getIduser(){
        return iduser;
    }

    public String getNombreuser() {
        return nombreuser;
    }

    public String getTipodocuser() {
        return tipodocuser;
    }

    public Integer getNumdocuser() {
        return numdocuser;
    }

    public String getCorreouser() {
        return correouser;
    }

<<<<<<< HEAD
=======
    // public Alojamiento getAlojamiento() {
    //     return alojamiento;
    // }
>>>>>>> 6ee3f843aee9efa79a8dbc49bbd1e4bdb4485a2a


    // Setters
    public void setIduser(Integer iduser){
        this.iduser = iduser;
    }

    public void setNombreuser(String nombreuser) {
        this.nombreuser = nombreuser;
    }

    public void setTipodocuser(String tipodocuser) {
        this.tipodocuser = tipodocuser;
    }

    public void setNumdocuser(Integer numdocuser) {
        this.numdocuser = numdocuser;
    }

    public void setCorreouser(String correouser) {
        this.correouser = correouser;
    }

<<<<<<< HEAD
=======
    // public void setAlojamiento(Alojamiento alojamiento) {
    //     this.alojamiento = alojamiento;
    // }

>>>>>>> 6ee3f843aee9efa79a8dbc49bbd1e4bdb4485a2a
}
