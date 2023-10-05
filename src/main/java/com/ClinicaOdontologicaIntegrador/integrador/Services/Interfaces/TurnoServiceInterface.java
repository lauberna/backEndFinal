package com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Turno;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.CrearTurnoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.EditarTurnoDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TurnoServiceInterface {
    Turno guardar(CrearTurnoDto x) throws Exception;

    List<Turno> listar() throws Exception;

    void eliminar(Long id) throws Exception;

    Optional<Turno> buscar(Long id) throws Exception;

    Turno editar(EditarTurnoDto x) throws Exception;

    boolean validarHora(LocalDateTime time);

    List<Turno> buscarPorPaciente(Long id) throws Exception;
    List<Turno> buscarPorOdontologo(Integer id) throws Exception;
}
