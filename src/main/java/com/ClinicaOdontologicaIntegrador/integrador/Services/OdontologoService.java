package com.ClinicaOdontologicaIntegrador.integrador.Services;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Odontologos;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.InvalidParameterExeption;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.ResourceNotFoundExeption;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.OdontologoServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.Utils.Mapper;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.CrearOdontologoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.OdontologoDto;
import com.ClinicaOdontologicaIntegrador.integrador.repository.OdontologoRep;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements OdontologoServiceInterface {

    private final OdontologoRep odontologoRep;

    @Autowired
    public OdontologoService(OdontologoRep odontologoRep) {
        this.odontologoRep = odontologoRep;
    }

    @Override
    public Odontologos guardar(CrearOdontologoDto dto) throws Exception {
        try {
            if (dto.getApellido() == "" || dto.getApellido() == null) {
                throw new InvalidParameterExeption("apellido invalido o faltante");
            }
            if (dto.getNombre() == "" || dto.getNombre() == null) {
                throw new InvalidParameterExeption("nombre invalido o faltante");
            }
            if (dto.getMatricula() == "" || dto.getMatricula() == null) {
                throw new InvalidParameterExeption("matricula invalida o faltante");
            }
            if (odontologoRep.existsByMatricula(dto.getMatricula())) {
                throw new InvalidParameterExeption("Odontologo con matricula: " + dto.getMatricula() + ", ya existente");
            }
            Odontologos odontologo = odontologoRep.save(Mapper.map(dto));
            return odontologo;
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public List<Odontologos> listar() throws Exception {
        try {
            List<Odontologos> odontologos = odontologoRep.findAll();
            if (odontologos.isEmpty()) throw new ResourceNotFoundExeption("No hay odontologos por listar");
            return odontologos;
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        try {
            if (id == null || id <= 0) throw new InvalidParameterExeption("Id invalido o faltante");
            if (!odontologoRep.existsById(id))
                throw new ResourceNotFoundExeption("No se encontro odontologo con id:" + id);
            odontologoRep.deleteById(id);
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public Optional<Odontologos> buscar(Integer id) throws Exception {
        try {
            if (id == null || id <= 0) throw new InvalidParameterExeption("Id invalido");
            Optional<Odontologos> odontologos = odontologoRep.findById(id);
            if (odontologos.isPresent()) return odontologos;
            else throw new ResourceNotFoundExeption("no se encontro odontologo con id:" + id);
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public Odontologos editar(OdontologoDto dto) throws Exception {
        try {
            if (dto.getId() == null || dto.getId() <= 0) throw new InvalidParameterExeption("Id invalido o faltante");
            if (!odontologoRep.findById(dto.getId()).isPresent()) {
                throw new ResourceNotFoundExeption("no se encontro odontologo con id: " + dto.getId());
            }
            if (dto.getApellido() == "" || dto.getApellido() == null)
                throw new InvalidParameterExeption("apellido invalido o faltante");
            if (dto.getMatricula() == "" || dto.getMatricula() == null)
                throw new InvalidParameterExeption("matricula invalida o faltante");
            if (dto.getNombre() == "" || dto.getNombre() == null)
                throw new InvalidParameterExeption("nombre invalido o faltante");
            Odontologos odontologos = odontologoRep.saveAndFlush(Mapper.map(dto));
            return odontologos;
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }
}
