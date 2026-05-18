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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.queboleteo.dto.ZonaConciertoDTO;
import co.edu.unbosque.queboleteo.service.ZonaConciertoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/zonaconcierto" })
@Tag(name = "Gestión de Zonas por Concierto", description = "Endpoints para la gestión de zonas asociadas a conciertos")
@SecurityRequirement(name = "bearerAuth")
public class ZonaConciertoController {

	@Autowired
	private ZonaConciertoService zonaConciertoService;

	/**
	 * Crea un nuevo registro ZonaConcierto.
	 *
	 * @param zonaConcierto DTO del registro
	 * @return Mensaje de éxito o error
	 */
	/*
	 * @Operation(summary = "Crear zona-concierto")
	 * 
	 * @PostMapping("/crear") public ResponseEntity<String> create(@RequestBody
	 * ZonaConciertoDTO zonaConcierto) { int status =
	 * zonaConciertoService.create(zonaConcierto); if (status == 0) { return new
	 * ResponseEntity<>("Zona-concierto creada correctamente", HttpStatus.CREATED);
	 * } return new ResponseEntity<>("Ya existe esa combinación zona-concierto",
	 * HttpStatus.NOT_ACCEPTABLE); }
	 */

	/**
	 * Obtiene todos los registros ZonaConcierto.
	 *
	 * @return Lista de registros
	 */
	@Operation(summary = "Obtener todos los registros zona-concierto")
	@GetMapping("/all")
	public ResponseEntity<List<ZonaConciertoDTO>> getAll() {
		List<ZonaConciertoDTO> lista = zonaConciertoService.getAll();
		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene un registro ZonaConcierto por ID.
	 *
	 * @param id ID del registro
	 * @return Registro encontrado
	 */
	@Operation(summary = "Obtener zona-concierto por ID")
	@GetMapping("/{id}")
	public ResponseEntity<ZonaConciertoDTO> getById(@PathVariable Long id) {
		ZonaConciertoDTO found = zonaConciertoService.getById(id);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza un registro ZonaConcierto existente.
	 *
	 * @param id            ID del registro
	 * @param zonaConcierto Nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar zona-concierto")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody ZonaConciertoDTO zonaConcierto) {
		int status = zonaConciertoService.updateById(id, zonaConcierto);
		if (status == 0) {
			return new ResponseEntity<>("Zona-concierto actualizada correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Zona-concierto no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina un registro ZonaConcierto por ID.
	 *
	 * @param id ID del registro
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar zona-concierto")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = zonaConciertoService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Zona-concierto eliminada correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Zona-concierto no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de registros ZonaConcierto.
	 *
	 * @return Cantidad total de registros
	 */
	@Operation(summary = "Contar registros zona-concierto")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(zonaConciertoService.count(), HttpStatus.OK);
	}

	@Operation(summary = "Crear zona-concierto")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody ZonaConciertoDTO zonaConcierto) {
		int status = zonaConciertoService.create(zonaConcierto);
		if (status == 0) {
			return new ResponseEntity<>("Zona-concierto creada correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe esa combinación zona-concierto", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>(
					"La zona no tiene lugares configurados. Ejecuta primero /zona/configurar-lugares/{idZona}",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Obtiene las zonas de un concierto
	 * 
	 * @param idConcierto
	 * @return
	 */
	@Operation(summary = "Obtener zonas de un concierto")
	@GetMapping("/concierto/{idConcierto}")
	public ResponseEntity<List<ZonaConciertoDTO>> getByConciertoId(@PathVariable Long idConcierto) {
	    List<ZonaConciertoDTO> lista = zonaConciertoService.getByConciertoId(idConcierto);
	    if (lista.isEmpty())
	        return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
	    return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	
	
	
}