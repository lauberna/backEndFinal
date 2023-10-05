package com.ClinicaOdontologicaIntegrador.integrador.repository;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRep extends JpaRepository<Paciente, Long> {
    Boolean existsByNombreAndApellido(String nombre, String Apellido);
    Boolean existsByDni(String dni);
}
