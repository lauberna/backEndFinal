package com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS;

import java.time.LocalDateTime;

public class CrearTurnoDto {
    private Integer idOd;
    private Long idPa;
    private LocalDateTime fecha;


    public CrearTurnoDto(Integer idOd, Long idPa, LocalDateTime fecha) {
        this.idOd = idOd;
        this.idPa = idPa;
        this.fecha = fecha;
    }

    public CrearTurnoDto() {
    }

    public Integer getIdOd() {
        return idOd;
    }

    public void setIdOd(Integer idOd) {
        this.idOd = idOd;
    }

    public Long getIdPa() {
        return idPa;
    }

    public void setIdPa(Long idPa) {
        this.idPa = idPa;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "TurnoDto{" +
                "idOd=" + idOd +
                ", idPa=" + idPa +
                ", fecha=" + fecha +
                '}';
    }
}


