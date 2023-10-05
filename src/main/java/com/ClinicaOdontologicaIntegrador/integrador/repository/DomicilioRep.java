package com.ClinicaOdontologicaIntegrador.integrador.repository;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomicilioRep extends JpaRepository<Domicilio, Long> {
}
