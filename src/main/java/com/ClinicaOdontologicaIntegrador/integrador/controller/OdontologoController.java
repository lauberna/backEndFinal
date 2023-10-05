package com.ClinicaOdontologicaIntegrador.integrador.controller;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Responses.ResponseObject;
import com.ClinicaOdontologicaIntegrador.integrador.Services.Interfaces.OdontologoServiceInterface;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.CrearOdontologoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.OdontologoDto;
import com.ClinicaOdontologicaIntegrador.integrador.repository.TurnosRep;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/odontologos")
public class OdontologoController {
    ObjectMapper objectMapper = new ObjectMapper();
    private final Logger log = Logger.getLogger(OdontologoController.class);

    private final OdontologoServiceInterface odontologoService;
    private final TurnosRep turnosRep;

    @Autowired
    public OdontologoController(TurnosRep turnosRep, OdontologoServiceInterface odontologoService) {
        this.odontologoService = odontologoService;
        this.turnosRep = turnosRep;
    }

    @Autowired
    private HttpServletRequest request;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping
    public ResponseEntity registrarOD(@RequestBody CrearOdontologoDto dto) throws Exception {
        OdontologoDto res = objectMapper.convertValue(odontologoService.guardar(dto), OdontologoDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(LocalDateTime.now(), HttpStatus.CREATED.value() + ", " + HttpStatus.CREATED.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @GetMapping
    public ResponseEntity listarOD() throws Exception {
            List<OdontologoDto> res = objectMapper.convertValue(odontologoService.listar(), objectMapper.getTypeFactory().constructCollectionType(List.class, OdontologoDto.class));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarOD(@PathVariable Integer id) throws Exception {
        odontologoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, "eliminado", request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping("/editar")
    public ResponseEntity editarOD(@RequestBody OdontologoDto dto) throws Exception {
            OdontologoDto res = objectMapper.convertValue(odontologoService.editar(dto), OdontologoDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }

    @Transactional(rollbackOn = Exception.class)
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarOD(@PathVariable Integer id) throws Exception {
        Optional<OdontologoDto> res = odontologoService.buscar(id).map(src -> objectMapper.convertValue(src, OdontologoDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.OK.value()) + ", " + HttpStatus.OK.getReasonPhrase(), true, res, request.getRequestURL()));
    }
}
