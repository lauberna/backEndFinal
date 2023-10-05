package com.ClinicaOdontologicaIntegrador.integrador.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ODONTOLOGOS")
public class Odontologos {
    @Column
    private String nombre;
    private String apellido;
    private String matricula;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "turnos")
    @OneToMany(mappedBy = "odontologos", cascade = CascadeType.ALL)
    private List<Turno> turno;

    @Override
    public String toString() {
        return "Odontologos{" +
                "nombre='" + nombre +
                ", apellido='" + apellido +
                ", matricula=" + matricula +
                ", id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
