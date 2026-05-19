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

import co.edu.unbosque.queboleteo.dto.OrganizadorDTO;
import co.edu.unbosque.queboleteo.service.OrganizadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de organizadores.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/organizador" })
@Tag(name = "Gestión de Organizadores", description = "Endpoints para la gestión de organizadores")
@SecurityRequirement(name = "bearerAuth")
public class OrganizadorController {

	@Autowired
	private OrganizadorService organizadorService;

	/**
	 * Crea un nuevo organizador.
	 * 
	 * @param organizador DTO del organizador
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Crear organizador")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody OrganizadorDTO organizador) {
		int status = organizadorService.create(organizador);
		if (status == 0) {
			return new ResponseEntity<>("Organizador creado correctamente", HttpStatus.CREATED);
		}
		if (status == 2) {
			return new ResponseEntity<>("El correo ingresado no es válido", HttpStatus.NOT_ACCEPTABLE);
		} else {

			return new ResponseEntity<>("Ya existe un organizador con ese nombre", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene todos los organizadores.
	 * 
	 * @return Lista de organizadores
	 */
	@Operation(summary = "Obtener todos los organizadores")
	@GetMapping("/all")
	public ResponseEntity<List<OrganizadorDTO>> getAll() {
		List<OrganizadorDTO> lista = organizadorService.getAll();
		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene un organizador por nombre.
	 * 
	 * @param nombre nombre del organizador
	 * @return Organizador encontrado
	 */
	@Operation(summary = "Obtener organizador por nombre")
	@GetMapping("/{nombre}")
	public ResponseEntity<OrganizadorDTO> getById(@PathVariable String nombre) {
		OrganizadorDTO found = organizadorService.getById(nombre);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza un organizador existente.
	 * 
	 * @param nombre      nombre del organizador
	 * @param organizador nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar organizador")
	@PutMapping("/update/{nombre}")
	public ResponseEntity<String> updateById(@PathVariable String nombre, @RequestBody OrganizadorDTO organizador) {
		int status = organizadorService.updateById(nombre, organizador);
		if (status == 0) {
			return new ResponseEntity<>("Organizador actualizado correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Organizador no encontrado", HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina un organizador por nombre.
	 * 
	 * @param nombre nombre del organizador
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar organizador")
	@DeleteMapping("/delete/{nombre}")
	public ResponseEntity<String> deleteById(@PathVariable String nombre) {
		int status = organizadorService.deleteById(nombre);
		if (status == 0) {
			return new ResponseEntity<>("Organizador eliminado correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Organizador no encontrado", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de organizadores.
	 * 
	 * @return Cantidad total de organizadores
	 */
	@Operation(summary = "Contar organizadores")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {

		return new ResponseEntity<>(organizadorService.count(), HttpStatus.OK);
	}

}