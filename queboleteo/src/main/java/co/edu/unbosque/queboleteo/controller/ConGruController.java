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

import co.edu.unbosque.queboleteo.dto.ConGruDTO;
import co.edu.unbosque.queboleteo.service.ConGruService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/congru" })
@Tag(name = "Concierto-Grupo", description = "Endpoints para la relación entre conciertos y grupos")
public class ConGruController {

	@Autowired
	private ConGruService conGruService;

	/**
	 * Asocia un grupo a un concierto.
	 *
	 * @param dto DTO con idConcierto e idGrupo
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Asociar grupo a concierto")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody ConGruDTO dto) {
		int status = conGruService.create(dto);
		if (status == 0)
			return new ResponseEntity<>("Asociación creada correctamente", HttpStatus.CREATED);
		if (status == 1)
			return new ResponseEntity<>("Concierto o grupo no encontrado", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>("Esa asociación ya existe", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todas las asociaciones concierto-grupo.
	 *
	 * @return Lista de asociaciones
	 */
	@Operation(summary = "Obtener todas las asociaciones concierto-grupo")
	@GetMapping("/all")
	public ResponseEntity<List<ConGruDTO>> getAll() {
		List<ConGruDTO> lista = conGruService.getAll();
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los grupos de un concierto.
	 *
	 * @param idConcierto ID del concierto
	 * @return Lista de asociaciones del concierto
	 */
	@Operation(summary = "Obtener grupos de un concierto")
	@GetMapping("/concierto/{idConcierto}")
	public ResponseEntity<List<ConGruDTO>> getByConciertoId(@PathVariable Long idConcierto) {
		List<ConGruDTO> lista = conGruService.getByConciertoId(idConcierto);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los conciertos de un grupo.
	 *
	 * @param idGrupo ID del grupo
	 * @return Lista de asociaciones del grupo
	 */
	@Operation(summary = "Obtener conciertos de un grupo")
	@GetMapping("/grupo/{idGrupo}")
	public ResponseEntity<List<ConGruDTO>> getByGrupoId(@PathVariable Long idGrupo) {
		List<ConGruDTO> lista = conGruService.getByGrupoId(idGrupo);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Elimina la asociación entre un concierto y un grupo.
	 *
	 * @param idConcierto ID del concierto
	 * @param idGrupo     ID del grupo
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar asociación concierto-grupo")
	@DeleteMapping("/{idConcierto}/{idGrupo}")
	public ResponseEntity<String> deleteByIds(@PathVariable Long idConcierto, @PathVariable Long idGrupo) {
		int status = conGruService.deleteByIds(idConcierto, idGrupo);
		if (status == 0)
			return new ResponseEntity<>("Asociación eliminada correctamente", HttpStatus.OK);
		return new ResponseEntity<>("Asociación no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de asociaciones concierto-grupo.
	 *
	 * @return Cantidad total de asociaciones
	 */
	@Operation(summary = "Contar asociaciones concierto-grupo")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(conGruService.count(), HttpStatus.OK);
	}
}