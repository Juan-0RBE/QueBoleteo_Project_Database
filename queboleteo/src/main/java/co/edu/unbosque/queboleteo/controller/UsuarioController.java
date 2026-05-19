package co.edu.unbosque.queboleteo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.queboleteo.dto.UsuarioDTO;
import co.edu.unbosque.queboleteo.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "*" })
@Transactional
@Tag(name = "Gestión de Usuarios", description = "Endpoints para administrar usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioServ;

	public UsuarioController() {
	}

	/**
	 * Crear usuario
	 */
	@Operation(summary = "Crear nuevo usuario")
	@PostMapping("/crear")
	public ResponseEntity<String> createUser(@RequestBody UsuarioDTO newUser) {
		int status = usuarioServ.create(newUser);
		if (status == 0) {
			return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);

		} else if (status == 1) {
			return new ResponseEntity<>("El nombre de usuario ya existe", HttpStatus.NOT_ACCEPTABLE);

		} else if (status == 2) {
			return new ResponseEntity<>("La contraseña no cumple los requisitos", HttpStatus.NOT_ACCEPTABLE);

		} else if (status == 3) {
			return new ResponseEntity<>("El correo ya está registrado", HttpStatus.NOT_ACCEPTABLE);

		} else {
			return new ResponseEntity<>("Error al crear usuario", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtener todos los usuarios
	 */
	@Operation(summary = "Obtener todos los usuarios")
	@GetMapping("/obtener/todos")
	public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
		List<UsuarioDTO> users = usuarioServ.getAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * Contar usuarios
	 */
	@Operation(summary = "Contar usuarios")
	@GetMapping("/contar")
	public ResponseEntity<Long> countUsers() {
		Long count = usuarioServ.count();
		return new ResponseEntity<>(count, HttpStatus.OK);
	}

	/**
	 * Buscar usuario por correo
	 */
	@Operation(summary = "Buscar usuario por correo")
	@GetMapping("/obtener/{correo}")
	public ResponseEntity<UsuarioDTO> getByCorreo(@PathVariable String correo) {
		UsuarioDTO found = usuarioServ.getByCorreo(correo);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>(new UsuarioDTO(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Buscar usuario por username
	 */
	@Operation(summary = "Buscar usuario por nombre de usuario")
	@GetMapping("/obtener/username/{username}")
	public ResponseEntity<UsuarioDTO> getByUsername(@PathVariable String username) {
		UsuarioDTO found = usuarioServ.getByUsername(username);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>(new UsuarioDTO(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Verificar existencia por correo
	 */
	@Operation(summary = "Verificar existencia de usuario")
	@GetMapping("/existe/{correo}")
	public ResponseEntity<Boolean> existsUser(@PathVariable String correo) {
		boolean found = usuarioServ.existByCorreo(correo);
		return new ResponseEntity<>(found, HttpStatus.OK);
	}

	/**
	 * Actualizar usuario
	 */
	@Operation(summary = "Actualizar usuario")
	@PutMapping("/actualizar/{correo}")
	public ResponseEntity<String> updateUser(@PathVariable String correo, @RequestBody UsuarioDTO newUser) {
		int status = usuarioServ.updateByCorreo(correo, newUser);
		if (status == 0) {
			return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.OK);

		} else if (status == 1) {
			return new ResponseEntity<>("El nombre de usuario ya está en uso", HttpStatus.CONFLICT);

		} else if (status == 2) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);

		} else {
			return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Actualizar contraseña
	 */
	@Operation(summary = "Actualizar contraseña")
	@PutMapping("/actualizar/clave/{correo}")
	public ResponseEntity<String> updatePassword(@PathVariable String correo, @RequestBody UsuarioDTO newData) {

		int status = usuarioServ.updatePasswordByCorreo(correo, newData);

		if (status == 0) {
			return new ResponseEntity<>("Contraseña actualizada exitosamente", HttpStatus.OK);

		} else if (status == 1) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);

		} else if (status == 2) {
			return new ResponseEntity<>("La contraseña no cumple los requisitos", HttpStatus.NOT_ACCEPTABLE);

		} else {
			return new ResponseEntity<>("Error al actualizar contraseña", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Verificar cuenta
	 */
	@Operation(summary = "Verificar cuenta de usuario")
	@PutMapping("/verificar/{correo}")
	public ResponseEntity<String> verifyUser(@PathVariable String correo) {
		UsuarioDTO usuario = usuarioServ.getByCorreo(correo);
		if (usuario == null) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		}
		if (usuario.getIsVerified()) {
			return new ResponseEntity<>("La cuenta ya está verificada", HttpStatus.OK);
		}
		usuario.setIsVerified(true);
		int status = usuarioServ.updateVerificationStatus(correo, usuario);
		if (status == 0) {
			return new ResponseEntity<>("Usuario verificado exitosamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Error al verificar usuario", HttpStatus.BAD_REQUEST);
	}

	/**
	 * Eliminar usuario por correo
	 */
	@Operation(summary = "Eliminar usuario por correo")
	@DeleteMapping("/eliminar/{correo}")
	public ResponseEntity<String> deleteByCorreo(@PathVariable String correo) {
		int status = usuarioServ.deleteByCorreo(correo);
		if (status == 0) {
			return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	}

	/**
	 * Eliminar usuario por username
	 */
	@Operation(summary = "Eliminar usuario por nombre de usuario")
	@DeleteMapping("/eliminar/username/{username}")
	public ResponseEntity<String> deleteByUsername(@PathVariable String username) {
		int status = usuarioServ.deleteByUsername(username);
		if (status == 0) {
			return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Obtener el correo de un usuario por su username")
	@GetMapping("/obtener/correo/{username}")
	public ResponseEntity<String> getCorreoByUsername(@PathVariable String username) {
		UsuarioDTO found = usuarioServ.getByUsername(username);
		if (found != null && found.getCorreo() != null) {
			return new ResponseEntity<>(found.getCorreo(), HttpStatus.OK);
		}

		return new ResponseEntity<>("Correo no encontrado", HttpStatus.NOT_FOUND);
	}
}