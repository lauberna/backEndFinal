package com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE;

import com.ClinicaOdontologicaIntegrador.integrador.dto.DOMICILIO.CrearDomicilioDto;


public class CrearPacienteDto {
    private String nombre;
    private String apellido;
    private String dni;
    private CrearDomicilioDto domicilio;


    public CrearPacienteDto(String nombre, String apellido, String dni, CrearDomicilioDto domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.domicilio = domicilio;

    }

    public CrearPacienteDto() {
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public CrearDomicilioDto getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(CrearDomicilioDto domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "PacienteDto{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", domicilio=" + domicilio + "}";
    }
}
