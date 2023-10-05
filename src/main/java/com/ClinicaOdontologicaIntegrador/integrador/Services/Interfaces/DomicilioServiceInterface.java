package com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Domicilio;

import java.util.List;
import java.util.Optional;

public interface DomicilioServiceInterface {
    Domicilio guardar(Domicilio x) throws Exception;
    List<Domicilio> listar() throws Exception;
    void eliminar(int id) throws Exception;
    Optional<Domicilio> buscar(Integer id) throws Exception;
    Domicilio editar(Domicilio x) throws Exception;
}
