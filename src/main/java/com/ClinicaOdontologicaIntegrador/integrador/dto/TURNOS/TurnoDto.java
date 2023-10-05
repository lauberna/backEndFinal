package com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Odontologos;
import com.ClinicaOdontologicaIntegrador.integrador.Entities.Paciente;


import java.time.LocalDateTime;

public class TurnoDto {

    private Long Id;
    private LocalDateTime fecha;
    private Paciente paciente;
    private Odontologos odontologos;


    public TurnoDto() {

    }

    @Override
    public String toString() {
        return "Turno{" +
                "Id=" + Id +
                ", paciente=" + paciente +
                ", odontologo=" + odontologos +
                ", fecha=" + fecha +
                '}';
    }

    public TurnoDto(Long id, Paciente paciente, Odontologos odontologos, LocalDateTime fecha) {
        Id = id;
        this.paciente = paciente;
        this.odontologos = odontologos;
        this.fecha = fecha;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologos getOdontologo() {
        return odontologos;
    }

    public void setOdontologo(Odontologos odontologo) {
        this.odontologos = odontologo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
