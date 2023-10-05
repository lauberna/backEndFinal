package com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Paciente;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.CrearPacienteDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.PacienteDto;

import java.util.List;
import java.util.Optional;

public interface PacienteServiceInterface {
    Paciente guardar(CrearPacienteDto x) throws Exception;
    List<Paciente> listar() throws Exception;
    void eliminar(Long id) throws Exception;
    Optional<Paciente> buscar(Long id) throws Exception;
    Paciente editar(PacienteDto x) throws Exception;
}
