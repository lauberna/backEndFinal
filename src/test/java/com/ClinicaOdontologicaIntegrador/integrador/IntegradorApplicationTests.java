package com.ClinicaOdontologicaIntegrador.integrador;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Odontologos;
import com.ClinicaOdontologicaIntegrador.integrador.Entities.Paciente;
import com.ClinicaOdontologicaIntegrador.integrador.Entities.Turno;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.ResourceNotFoundExeption;
import com.ClinicaOdontologicaIntegrador.integrador.Services.OdontologoService;
import com.ClinicaOdontologicaIntegrador.integrador.Services.PacienteService;
import com.ClinicaOdontologicaIntegrador.integrador.Services.TurnoService;
import com.ClinicaOdontologicaIntegrador.integrador.dto.DOMICILIO.CrearDomicilioDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.ODONTOLOGOS.CrearOdontologoDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.PACIENTE.CrearPacienteDto;
import com.ClinicaOdontologicaIntegrador.integrador.dto.TURNOS.CrearTurnoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class IntegradorApplicationTests {


	@Autowired
	private OdontologoService odontologoService;

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private TurnoService turnoService;

	@Test
	void guardar() throws Exception {
		Odontologos odontologosGuardado = odontologoService.guardar(new CrearOdontologoDto("laureano", "berna", "ABC123"));
		Assertions.assertNotNull(odontologoService.buscar(odontologosGuardado.getId()));
	}

	@Test
	void buscar() throws Exception {
		Odontologos odontologoGuardado = odontologoService.guardar(new CrearOdontologoDto("laureano", "berna", "ABC1234"));
		Optional<Odontologos> odontologoBuscado = odontologoService.buscar(odontologoGuardado.getId());
		assertEquals(odontologoGuardado.getNombre(), odontologoBuscado.get().getNombre());
	}

	@Test
	void eliminar() throws Exception {

		Odontologos odontologoGuardado = odontologoService.guardar(new CrearOdontologoDto("laureano", "berna", "ABC12345"));
		odontologoService.eliminar(odontologoGuardado.getId());
		assertThrows(ResourceNotFoundExeption.class, () -> odontologoService.buscar(odontologoGuardado.getId()));
	}

	@Test
	void guardarP() throws Exception {
		Paciente pacienteGuardado = pacienteService.guardar(new CrearPacienteDto("laureano", "berna", "44538355", new CrearDomicilioDto("la plata", "mendoza")));
		Assertions.assertNotNull(pacienteService.buscar(pacienteGuardado.getId()));
	}

	@Test
	void buscarP() throws Exception {
		Paciente pacienteGuardado = pacienteService.guardar(new CrearPacienteDto("laureano", "berna", "445383555", new CrearDomicilioDto("la plata", "mendoza")));
		Optional<Paciente> pacienteBuscado = pacienteService.buscar(pacienteGuardado.getId());
		assertEquals(pacienteGuardado.getNombre(), pacienteBuscado.get().getNombre());
	}

	@Test
	void eliminarP() throws Exception {
		Paciente pacienteGuardado = pacienteService.guardar(new CrearPacienteDto("laureano", "berna", "445383555", new CrearDomicilioDto("la plata", "mendoza")));
		pacienteService.eliminar(pacienteGuardado.getId());
		assertThrows(ResourceNotFoundExeption.class, () -> pacienteService.buscar(pacienteGuardado.getId()));
	}

	@Test
	void guardarT() throws Exception {
		Odontologos odontologoGuardado = odontologoService.guardar(new CrearOdontologoDto("laureano", "berna", "ABC123454"));
		Turno turnoGuardado = turnoService.guardar(new CrearTurnoDto(1,1l, LocalDateTime.of(2024, 11, 23, 12, 30,00)));
		Assertions.assertNotNull(turnoService.buscar(turnoGuardado.getId()));
	}

	@Test
	void buscarT() throws Exception {
		Turno turnoGuardado = turnoService.guardar(new CrearTurnoDto(1,1l, LocalDateTime.of(2024, 11, 23, 12, 30,00)));
		Optional<Turno> turnoBuscado = turnoService.buscar(turnoGuardado.getId());
		assertEquals(turnoGuardado.getFecha(), turnoBuscado.get().getFecha());
	}

	@Test
	void eliminarT() throws Exception {
		Turno turnoGuardado = turnoService.guardar(new CrearTurnoDto(1,1l, LocalDateTime.of(2024, 11, 23, 12, 30,00)));
		turnoService.eliminar(turnoGuardado.getId());
		assertThrows(ResourceNotFoundExeption.class, () -> turnoService.buscar(turnoGuardado.getId()));
	}

}
