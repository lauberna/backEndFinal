package com.ClinicaOdontologicaIntegrador.integrador.Entities;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TURNOS")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private LocalDateTime fecha;
    @ManyToOne()
    @JoinColumn(name = "PACIETNE_ID")
    private Paciente paciente;
    @ManyToOne()
    @JoinColumn(name = "ODONTOLOGO_ID")
    private Odontologos odontologos;

    @Override
    public String toString() {
        return "Turno{" +
                "Id=" + Id +
                ", paciente=" + paciente +
                ", odontologo=" + odontologos +
                ", fecha=" + fecha +
                '}';
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
        return  fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
