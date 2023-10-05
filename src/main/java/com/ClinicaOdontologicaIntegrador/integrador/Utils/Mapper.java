package com.ClinicaOdontologicaIntegrador.integrador.Utils;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Domicilio;
import com.ClinicaOdontologicaIntegrador.integrador.Entities.Odontologos;
import com.ClinicaOdontologicaIntegrador.integrador.Entities.Paciente;
import com.ClinicaOdontologicaIntegrador.integrador.Entities.Turno;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.CrearOdontologoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.OdontologoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.CrearPacienteDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.PacienteDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.TurnoDto;

import java.time.LocalDate;

public class Mapper {

    public static Turno map(TurnoDto dto) throws Exception {
        Turno turno = new Turno();
        turno.setFecha(dto.getFecha());
        turno.setOdontologo(dto.getOdontologo());
        turno.setPaciente(dto.getPaciente());
        turno.setId(dto.getId());

        return turno;
    }

    public static Odontologos map(CrearOdontologoDto dto) {
        Odontologos odontologo = new Odontologos();

        odontologo.setApellido(dto.getApellido());
        odontologo.setNombre(dto.getNombre());
        odontologo.setMatricula(dto.getMatricula());

        return odontologo;
    }

    public static OdontologoDto mapAlt(CrearOdontologoDto dto) {
        OdontologoDto odontologoDto = new OdontologoDto();

        odontologoDto.setApellido(dto.getApellido());
        odontologoDto.setNombre(dto.getNombre());
        odontologoDto.setMatricula(dto.getMatricula());

        return odontologoDto;
    }

    public static Odontologos map(OdontologoDto dto) {
        Odontologos odontologo = new Odontologos();

        odontologo.setId(dto.getId());
        odontologo.setApellido(dto.getApellido());
        odontologo.setNombre(dto.getNombre());
        odontologo.setMatricula(dto.getMatricula());

        return odontologo;
    }

    public static Paciente map(PacienteDto dto) {
        Paciente paciente = new Paciente();
        Domicilio domicilio = new Domicilio();

        domicilio.setId(dto.getDomicilio().getId());
        domicilio.setCiudad(dto.getDomicilio().getCiudad());
        domicilio.setCalle(dto.getDomicilio().getCalle());

        paciente.setId(dto.getId());
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setDni(dto.getDni());
        paciente.setFechaIngreso(dto.getFechaIngreso());
        paciente.setDomicilio(domicilio);

        return paciente;
    }

    public static Paciente map(CrearPacienteDto dto) {
        Paciente paciente = new Paciente();
        Domicilio domicilio = new Domicilio();

        domicilio.setCalle(dto.getDomicilio().getCalle());
        domicilio.setCiudad(dto.getDomicilio().getCiudad());

        paciente.setApellido(dto.getApellido());
        paciente.setDni(dto.getDni());
        paciente.setNombre(dto.getNombre());
        paciente.setFechaIngreso(LocalDate.now());
        paciente.setDomicilio(domicilio);

        return paciente;
    }
}
