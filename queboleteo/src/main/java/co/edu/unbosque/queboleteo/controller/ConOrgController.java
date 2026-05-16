package co.edu.unbosque.queboleteo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.queboleteo.dto.ConOrgDTO;
import co.edu.unbosque.queboleteo.service.ConOrgService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/conorg" })
@Tag(name = "Concierto-Organizador", description = "Endpoints para la relación entre conciertos y organizadores")
@SecurityRequirement(name = "bearerAuth")
public class ConOrgController {

	@Autowired
	private ConOrgService conOrgService;

	/**
	 * Asocia un organizador a un concierto.
	 *
	 * @param dto DTO con idConcierto y nombreOrganizador
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Asociar organizador a concierto")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody ConOrgDTO dto) {
		int status = conOrgService.create(dto);
		if (status == 0)
			return new ResponseEntity<>("Asociación creada correctamente", HttpStatus.CREATED);
		if (status == 1)
			return new ResponseEntity<>("Concierto o organizador no encontrado", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>("Esa asociación ya existe", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todas las asociaciones concierto-organizador.
	 *
	 * @return Lista de asociaciones
	 */
	@Operation(summary = "Obtener todas las asociaciones concierto-organizador")
	@GetMapping("/all")
	public ResponseEntity<List<ConOrgDTO>> getAll() {
		List<ConOrgDTO> lista = conOrgService.getAll();
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los organizadores de un concierto.
	 *
	 * @param idConcierto ID del concierto
	 * @return Lista de asociaciones del concierto
	 */
	@Operation(summary = "Obtener organizadores de un concierto")
	@GetMapping("/concierto/{idConcierto}")
	public ResponseEntity<List<ConOrgDTO>> getByConciertoId(@PathVariable Long idConcierto) {
		List<ConOrgDTO> lista = conOrgService.getByConciertoId(idConcierto);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los conciertos de un organizador.
	 *
	 * @param nombreOrganizador Nombre del organizador
	 * @return Lista de asociaciones del organizador
	 */
	@Operation(summary = "Obtener conciertos de un organizador")
	@GetMapping("/organizador/{nombreOrganizador}")
	public ResponseEntity<List<ConOrgDTO>> getByOrganizadorNombre(@PathVariable String nombreOrganizador) {
		List<ConOrgDTO> lista = conOrgService.getByOrganizadorNombre(nombreOrganizador);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Elimina la asociación entre un concierto y un organizador.
	 *
	 * @param idConcierto       ID del concierto
	 * @param nombreOrganizador Nombre del organizador
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar asociación concierto-organizador")
	@DeleteMapping("/{idConcierto}/{nombreOrganizador}")
	public ResponseEntity<String> deleteByIds(@PathVariable Long idConcierto, @PathVariable String nombreOrganizador) {
		int status = conOrgService.deleteByIds(idConcierto, nombreOrganizador);
		if (status == 0)
			return new ResponseEntity<>("Asociación eliminada correctamente", HttpStatus.OK);
		return new ResponseEntity<>("Asociación no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de asociaciones concierto-organizador.
	 *
	 * @return Cantidad total de asociaciones
	 */
	@Operation(summary = "Contar asociaciones concierto-organizador")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(conOrgService.count(), HttpStatus.OK);
	}
}