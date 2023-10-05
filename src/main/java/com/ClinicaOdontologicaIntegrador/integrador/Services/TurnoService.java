package com.ClinicaOdontologicaIntegrador.integrador.Services;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Turno;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.InvalidParameterExeption;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.ResourceNotFoundExeption;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.OdontologoServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.PacienteServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.TurnoServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.CrearTurnoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.EditarTurnoDto;
import com.ClinicaOdontologicaIntegrador.integrador.repository.TurnosRep;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements TurnoServiceInterface {
    private static final Logger log = Logger.getLogger(TurnoService.class);
    private TurnosRep turnosRep;
    private OdontologoServiceInterface odontologoServiceInterface;
    private PacienteServiceInterface pacienteServiceInterface;

    @Autowired
    public TurnoService(TurnosRep turnosRep, OdontologoServiceInterface odontologoServiceInterface, PacienteServiceInterface pacienteServiceInterface) {
        this.turnosRep = turnosRep;
        this.odontologoServiceInterface = odontologoServiceInterface;
        this.pacienteServiceInterface = pacienteServiceInterface;
    }

    @Override
    public Turno guardar(CrearTurnoDto x) throws Exception {
        try {
            if (x.getFecha() == null || x.getFecha().isBefore(LocalDateTime.now()))
                throw new InvalidParameterExeption("fehca invalida o faltante");
            if (x.getIdOd() == null || x.getIdOd() <= 0) throw new InvalidParameterExeption("id invalido o faltante");
            if (x.getIdPa() == null || x.getIdPa() <= 0)
                throw new InvalidParameterExeption("fehca invalida o faltante");
            Turno turno = new Turno();
            turno.setOdontologo(odontologoServiceInterface.buscar(x.getIdOd()).get());
            turno.setPaciente(pacienteServiceInterface.buscar(x.getIdPa()).get());
            turno.setFecha(x.getFecha());
            if (turnosRep.findAllByPaciente(pacienteServiceInterface.buscar(x.getIdPa())).size() <= 1) {
                return turnosRep.save(turno);
            } else {
                throw new InvalidParameterExeption("ya posee el limite de turnos (2) disponobles para el paciente: " + turno.getPaciente().getNombre() + ", eliminelos o pruebe editandolos");
            }
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public List<Turno> listar() throws Exception {
        try {
            List<Turno> turnos = turnosRep.findAll();
            if (turnos.isEmpty()) throw new ResourceNotFoundExeption("no hay Turnos por listar");
            return turnos;
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        try {
            if (id == null || id <= 0) throw new InvalidParameterExeption("Id invalido o faltante");
            if (!turnosRep.existsById(id)) throw new ResourceNotFoundExeption("No se encontro turno con id:" + id);
            turnosRep.deleteById(id);
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public Optional<Turno> buscar(Long id) throws Exception {
        try {
            if (id == null || id <= 0) throw new InvalidParameterExeption("Id invalido o faltante");
            Optional<Turno> turno = turnosRep.findById(id);
            if (!turno.isPresent()) throw new ResourceNotFoundExeption("no se encontro ningun Turno con id: " + id);
            return turno;
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public Turno editar(EditarTurnoDto x) throws Exception {
        try {
            if (x.getFecha() == null || x.getFecha().isBefore(LocalDateTime.now()))
                throw new InvalidParameterExeption("fecha invalida o faltante");
            if (x.getIdOd() <= 0 || x.getIdOd() == null)
                throw new InvalidParameterExeption("id odontologo invalido o faltante");
            if (x.getIdPa() <= 0 || x.getIdPa() == null)
                throw new InvalidParameterExeption("id paciente o faltante");
            if (x.getId() <= 0 || x.getId() == null) throw new InvalidParameterExeption("id invalido o faltante");
            buscar(x.getId());
            Turno turno = new Turno();
            turno.setId(x.getId());
            turno.setFecha(x.getFecha());
            turno.setOdontologo(odontologoServiceInterface.buscar(x.getIdOd()).get());
            turno.setPaciente(pacienteServiceInterface.buscar(x.getIdPa()).get());
            if (turnosRep.findAllByPaciente(pacienteServiceInterface.buscar(x.getIdPa())).size() <= 1) {
                return turnosRep.saveAndFlush(turno);
            } else {
                throw new InvalidParameterExeption("ya posee el limite de turnos (2) disponobles para el odontologo :" + turno.getOdontologo().getNombre() + " y paciente: " + turno.getPaciente().getNombre() + ", eliminelos o pruebe editandolos");
            }

        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }
    }

    @Override
    public boolean validarHora(LocalDateTime time) {
        return true;
    }

    @Override
    public List<Turno> buscarPorPaciente(Long id) throws Exception {
        try {
            if (id <= 0 || id == null) throw new InvalidParameterExeption("id invalido o faltante");
            List<Turno> turnosPaciente = turnosRep.findAllByPacienteId(id);
            if (turnosPaciente.isEmpty()) throw new ResourceNotFoundExeption("no hay Turnos por listar");
            return turnosPaciente;
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        } catch (InvalidParameterExeption e) {
            throw new InvalidParameterExeption(e.getMessage());
        }
    }

    @Override
    public List<Turno> buscarPorOdontologo(Integer id) {
        try {
            if (id <= 0 || id == null) throw new InvalidParameterExeption("id invalido o faltante");
            List<Turno> turnosOdontologo = turnosRep.findAllByOdontologosId(id);
            if (turnosOdontologo.isEmpty()) throw new ResourceNotFoundExeption("no hay Turnos por listar");
            return turnosOdontologo;
        } catch (ResourceNotFoundExeption e) {
            throw new ResourceNotFoundExeption(e.getMessage());
        } catch (HibernateException e) {
            throw new HibernateException("algo fallo con hibernate", e);
        }catch (InvalidParameterExeption e){
            throw new InvalidParameterExeption(e.getMessage());
        }
    }
}
