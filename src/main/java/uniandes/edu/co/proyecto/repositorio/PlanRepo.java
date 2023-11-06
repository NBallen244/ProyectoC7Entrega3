package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Plan;

public interface PlanRepo extends JpaRepository <Plan, Integer> {

    // Creation
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO planes (idplan, tipoplan, descuento) VALUES (parranderos_sequence.nextval, :tipoplan, :descuento)", nativeQuery = true)
    void insertarPlan(@Param("tipoplan") String tipoplan, @Param("descuento") Float descuento);


    // Read
    @Query(value = "SELECT * FROM planes", nativeQuery = true)
    Collection<Plan> darPlanes();

    @Query(value = "SELECT * FROM planes WHERE idplan = :idplan", nativeQuery = true)
    Plan darPlan(@Param("idplan") Integer idplan);


    // Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE planes SET tipoplan=:tipoplan, descuento=:descuento WHERE idplan=:idplan", nativeQuery = true)
    void actualizarPlan(@Param("idplan") Integer idplan, @Param("tipoplan") String tipoplan, @Param("descuento") Float descuento);


    // Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM planes WHERE idplan =: idplan", nativeQuery = true)
    void eliminarPlan(@Param("idplan") Integer idplan);
}