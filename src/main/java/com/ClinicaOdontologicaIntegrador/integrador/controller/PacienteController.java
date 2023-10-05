package com.ClinicaOdontologicaIntegrador.integrador.controller;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Responses.ResponseObject;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.DomicilioServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.PacienteServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.CrearPacienteDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.PacienteDto;
import com.ClinicaOdontologicaIntegrador.integrador.repository.PacienteRep;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final Logger LOGGER = Logger.getLogger(PacienteController.class);
    private ObjectMapper objectMapper = new ObjectMapper();


    private final DomicilioServiceInterface domicilioServiceInterface;
    private final PacienteServiceInterface pacienteService;

    @Autowired
    public PacienteController(DomicilioServiceInterface domicilioServiceInterface, PacienteServiceInterface pacienteService) {
        this.domicilioServiceInterface = domicilioServiceInterface;
        this.pacienteService = pacienteService;
    }

    @Autowired
    private PacienteRep pacienteRep;

    @Autowired
    private HttpServletRequest request;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity crearPaciente(@RequestBody CrearPacienteDto dto) throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        PacienteDto res = objectMapper.convertValue(pacienteService.guardar(dto), PacienteDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.CREATED.value()) + ", " + HttpStatus.CREATED.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPaciente(@PathVariable Long id) throws Exception {
        pacienteService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, "eliminado", request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping("/editar")
    public ResponseEntity editarPaciente(@RequestBody PacienteDto dto) throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        PacienteDto res = objectMapper.convertValue(pacienteService.editar(dto), PacienteDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @GetMapping
    public ResponseEntity listarPacientes() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        List<PacienteDto> res = objectMapper.convertValue(pacienteService.listar(), objectMapper.getTypeFactory().constructCollectionType(List.class, PacienteDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPaciente(@PathVariable Long id) throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        Optional<PacienteDto> res = pacienteService.buscar(id).map(src -> objectMapper.convertValue(src, PacienteDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }
}
