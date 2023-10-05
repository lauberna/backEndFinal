package com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Odontologos;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.CrearOdontologoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.OdontologoDto;

import java.util.List;
import java.util.Optional;

public interface OdontologoServiceInterface {
    Odontologos guardar(CrearOdontologoDto x) throws Exception;
    List<Odontologos> listar() throws Exception;
    void eliminar(Integer id) throws Exception;
    Optional<Odontologos> buscar(Integer id) throws Exception;
    Odontologos editar(OdontologoDto x) throws Exception;
}
