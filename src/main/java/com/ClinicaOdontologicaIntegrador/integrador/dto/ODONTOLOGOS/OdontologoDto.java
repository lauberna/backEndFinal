package com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS;

public class OdontologoDto {
    private String nombre;
    private String apellido;
    private String matricula;
    private Integer id;

    public OdontologoDto(String nombre, String apellido, String matricula, Integer id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Odontologos{" +
                "nombre='" + nombre +
                ", apellido='" + apellido +
                ", matricula=" + matricula +
                ", id=" + id +
                '}';
    }

    public OdontologoDto() {
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
