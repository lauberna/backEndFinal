package com.ClinicaOdontologicaIntegrador.integrador.controller;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Responses.ResponseObject;
import com.ClinicaOdontologicaIntegrador.integrador.Entities.Turno;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.TurnoServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.CrearTurnoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.EditarTurnoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.TurnoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
@RequestMapping("/turnos")
public class TurnoController {
    ObjectMapper objectMapper = new ObjectMapper();
    private final TurnoServiceInterface turnoService;

    @Autowired
    public TurnoController(TurnoServiceInterface turnoService) {
        this.turnoService = turnoService;
    }

    @Autowired
    private HttpServletRequest request;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity guardarTurno(@RequestBody CrearTurnoDto dto) throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        TurnoDto res = objectMapper.convertValue(turnoService.guardar(dto), TurnoDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(LocalDateTime.now(), HttpStatus.CREATED.value() + ", " + HttpStatus.CREATED.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @GetMapping
    public ResponseEntity listarTurnos() throws Exception {
        List<Turno> res = objectMapper.convertValue(turnoService.listar(), objectMapper.getTypeFactory().constructCollectionType(List.class, TurnoDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTurno(@PathVariable Long id) throws Exception {
        turnoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, "eliminado", request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarTurno(@PathVariable Long id) throws Exception {
        Optional<TurnoDto> res = turnoService.buscar(id).map(src -> objectMapper.convertValue(src, TurnoDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping("/editar")
    public ResponseEntity editarTurno(@RequestBody EditarTurnoDto dto) throws Exception {
        TurnoDto res = objectMapper.convertValue(turnoService.editar(dto), TurnoDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @GetMapping("/paciente/{id}")
    public ResponseEntity buscarTurnosPaciente(@PathVariable Long id) throws Exception {
        List<Turno> res = objectMapper.convertValue(turnoService.buscarPorPaciente(id), objectMapper.getTypeFactory().constructCollectionType(List.class, TurnoDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @GetMapping("/odontologo/{id}")
    public ResponseEntity buscarTurnosOdontologo(@PathVariable Integer id) throws Exception {
        List<Turno> res = objectMapper.convertValue(turnoService.buscarPorOdontologo(id), objectMapper.getTypeFactory().constructCollectionType(List.class, TurnoDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

}
