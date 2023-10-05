package com.ClinicaOdontologicaIntegrador.integrador.Services;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Usuario;
import com.ClinicaOdontologicaIntegrador.integrador.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public Usuario guardar(Usuario d) {
        return usuarioRepository.save(d);
    }

    public Usuario buscar(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

}
