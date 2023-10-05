package com.ClinicaOdontologicaIntegrador.integrador.repository;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
