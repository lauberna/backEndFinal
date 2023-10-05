package com.ClinicaOdontologicaIntegrador.integrador.dto.DOMICILIO;

public class CrearDomicilioDto {

    private String calle;
    private String ciudad;

    public CrearDomicilioDto(String calle, String ciudad) {
        this.calle = calle;
        this.ciudad = ciudad;
    }
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "DomicilioDto{" +
                "calle='" + calle + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
