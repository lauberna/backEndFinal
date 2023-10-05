package com.ClinicaOdontologicaIntegrador.integrador.Services;


import com.ClinicaOdontologicaIntegrador.integrador.Entities.Domicilio;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.DomicilioServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.repository.DomicilioRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService implements DomicilioServiceInterface {
    private final DomicilioRep domicilioRep;

    @Autowired
    public DomicilioService(DomicilioRep domicilioRep) {
        this.domicilioRep = domicilioRep;
    }


    @Override
    public Domicilio guardar(Domicilio x) throws Exception {
        return domicilioRep.save(x);
    }

    @Override
    public List<Domicilio> listar() throws Exception {
        return null;
    }

    @Override
    public void eliminar(int id) throws Exception {

    }

    @Override
    public Optional<Domicilio> buscar(Integer id) throws Exception {
        return Optional.empty();
    }

    @Override
    public Domicilio editar(Domicilio x) throws Exception {
        return null;
    }
}
