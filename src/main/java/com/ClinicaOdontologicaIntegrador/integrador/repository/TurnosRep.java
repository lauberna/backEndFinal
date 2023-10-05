package com.ClinicaOdontologicaIntegrador.integrador.repository;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Paciente;
import com.ClinicaOdontologicaIntegrador.integrador.Entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TurnosRep extends JpaRepository<Turno, Long> {
    List<Optional<Turno>> findAllByPaciente(Optional<Paciente> pa);

    @Query("SELECT t FROM Turno t WHERE t.paciente.id = :pacienteId")
    List<Turno> findAllByPacienteId(Long pacienteId);

    @Query("SELECT t FROM Turno t WHERE t.odontologos.id = :odontologoId")
    List<Turno> findAllByOdontologosId(Integer odontologoId);
}
