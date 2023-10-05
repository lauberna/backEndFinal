package com.ClinicaOdontologicaIntegrador.integrador.Services;


import com.ClinicaOdontologicaIntegrador.integrador.Entities.Paciente;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.InvalidParameterExeption;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.ResourceNotFoundExeption;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.DomicilioServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.PacienteServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.Utils.Mapper;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.CrearPacienteDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.PacienteDto;
import com.ClinicaOdontologicaIntegrador.integrador.repository.PacienteRep;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements PacienteServiceInterface {
    private ObjectMapper objectMapper = new ObjectMapper();
    private PacienteRep pacienteRep;
    private DomicilioServiceInterface domicilioServiceInterface;
    private static final Logger log = Logger.getLogger(PacienteService.class);

    @Autowired
    public PacienteService(PacienteRep pacienteRep, DomicilioServiceInterface domicilioServiceInterface) {
        this.pacienteRep = pacienteRep;
        this.domicilioServiceInterface = domicilioServiceInterface;
    }

    @Override
    public Paciente guardar(CrearPacienteDto x) throws Exception {
        try {
            if (x.getNombre() == "" || x.getNombre() == null) {
                throw new InvalidParameterExeption("nombre invalido o faltante");
            }
            if (x.getApellido() == "" || x.getApellido() == null) {
                throw new InvalidParameterExeption("apellido invalido o faltante");
            }
            if (x.getDni() == "" || x.getDni() == null) {
                throw new InvalidParameterExeption("dni invalido o faltante");
            }
            if (x.getDomicilio() == null) {
                throw new InvalidParameterExeption("direccion invalida o faltante");
            }
            if (x.getDomicilio().getCalle() == "" || x.getDomicilio().getCiudad() == "" || x.getDomicilio().getCalle() == null || x.getDomicilio().getCiudad() == null) {
                throw new InvalidParameterExeption("datos de la direccion invalidos o faltantes");
            }
            if (pacienteRep.existsByDni(x.getDni())) {
                throw new InvalidParameterExeption("Paciente con dni: " + x.getDni() + ", ya existente");
            }
            Paciente paciente = pacienteRep.save(Mapper.map(x));
            return paciente;
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public List<Paciente> listar() {
        try {
            List<Paciente> pacientes = pacienteRep.findAll();
            if (pacientes.isEmpty()) throw new ResourceNotFoundExeption("No hay pacientes por listar");
            return pacientes;
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            if (id == null || id <= 0) throw new InvalidParameterExeption("Id invalido o faltante");
            if (!pacienteRep.existsById(id))
                throw new ResourceNotFoundExeption("No se encontro paciente con id:" + id);
            pacienteRep.deleteById(id);
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public Optional<Paciente> buscar(Long id) throws Exception {
        try {
            if (id == null || id <= 0) throw new InvalidParameterExeption("Id invalido");
            Optional<Paciente> paciente = pacienteRep.findById(id);
            if (paciente.isPresent()) return paciente;
            else throw new ResourceNotFoundExeption("no se encontro paciente con id:" + id);
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public Paciente editar(PacienteDto dto) throws Exception {
        try {
            if (dto.getId() == null || dto.getId() <= 0) throw new InvalidParameterExeption("Id invalido o faltante");
            if (!pacienteRep.findById(dto.getId()).isPresent()) {
                throw new ResourceNotFoundExeption("no se encontro paciente con id: " + dto.getId());
            }
            if (dto.getApellido() == "" || dto.getApellido() == null) {
                throw new InvalidParameterExeption("apellido invalido o faltante");
            }
            if (dto.getNombre() == "" || dto.getNombre() == null) {
                throw new InvalidParameterExeption("nombre invalido o faltante");
            }
            if (dto.getDomicilio() == null) {
                throw new InvalidParameterExeption("direccion invalida o faltante");
            }
            if (dto.getDomicilio().getCalle() == "" || dto.getDomicilio().getCiudad() == "" || dto.getDomicilio().getCalle() == null || dto.getDomicilio().getCiudad() == null) {
                throw new InvalidParameterExeption("datos de la direccion invalidos o faltantes");
            }
            if (dto.getFechaIngreso() == null) throw new InvalidParameterExeption("Fecha ingreso invalida o faltante");
            if (dto.getDomicilio().getId() == null || dto.getDomicilio().getId() <= 0)
                throw new InvalidParameterExeption("Id de direccion invalido o faltante");
            Paciente paciente = pacienteRep.saveAndFlush(Mapper.map(dto));
            return paciente;
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }
}
