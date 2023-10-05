package com.ClinicaOdontologicaIntegrador.integrador.repository;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Odontologos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRep extends JpaRepository<Odontologos, Integer> {
    Boolean existsByNombreAndApellido(String nombre, String Apellido);
    Boolean existsByMatricula(String matricula);
}
