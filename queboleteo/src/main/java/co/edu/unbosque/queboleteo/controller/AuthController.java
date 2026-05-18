package co.edu.unbosque.queboleteo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.queboleteo.dto.UsuarioDTO;
import co.edu.unbosque.queboleteo.entity.Usuario;
import co.edu.unbosque.queboleteo.security.JwtUtil;
import co.edu.unbosque.queboleteo.service.EmailService;
import co.edu.unbosque.queboleteo.service.UsuarioService;

/**
 * Controlador REST para la autenticación de usuarios. Maneja las operaciones de
 * inicio de sesión y registro de usuarios.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "API para autenticación de usuarios (login y registro)")
@CrossOrigin(origins = { "*" })
public class AuthController {

	private final EmailService emailService;

	/** Gestor de autenticación para validar credenciales de usuario. */
	private final AuthenticationManager authenticationManager;

	/** Utilidad para operaciones con tokens JWT. */
	private final JwtUtil jwtUtil;

	/** Servicio para operaciones relacionadas con usuarios. */
	private final UsuarioService userService;

	/**
	 * Servicio para enviar correos
	 */
	@Autowired
	private EmailService emailServ;

	/**
	 * Constructor que inicializa las dependencias necesarias para el controlador.
	 *
	 * @param authenticationManager Gestor de autenticación
	 * @param jwtUtil               Utilidad para tokens JWT
	 * @param userService           Servicio de usuarios
	 */
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService userService,
			EmailService emailService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
		this.emailService = emailService;
	}

	/**
	 * Maneja las solicitudes de inicio de sesión. Autentica al usuario y genera un
	 * token JWT si las credenciales son válidas.
	 *
	 * @param loginRequest DTO con las credenciales de inicio de sesión (nombre de
	 *                     usuario y contraseña)
	 * @return ResponseEntity con el token JWT y el rol del usuario si la
	 *         autenticación es exitosa, o un mensaje de error si falla
	 */
	@Operation(summary = "Iniciar sesión de usuario", description = """
			    Este endpoint permite a los usuarios de virus total iniciar sesión en el sistema proporcionando sus credenciales.

			    **¿Qué hace?** Verifica las credenciales del usuario y, si son correctas, genera un token JWT
			    que se utilizará para autenticar solicitudes posteriores.

			    **Paso a paso:**

			    1. Envía tu nombre de usuario y contraseña en formato JSON
			    2. Si las credenciales son correctas, recibirás un token JWT
			    3. Guarda este token para usarlo en futuras peticiones
			    4. Para usar el token, inclúyelo en el encabezado de autorización: `Authorization: Bearer tu_token_jwt`

			    **Nota:** El token tiene un tiempo de expiración limitado. Si expira, necesitarás iniciar sesión nuevamente.
			""")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class), examples = @ExampleObject(value = """
					    {
					      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
					      "role": "Usuario"
					    }
					"""))),
			@ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Nombre de usuario o contraseña inválidos o usuario no"
					+ " encontrado"))) })
	@PostMapping("/login")
	public ResponseEntity<?> login(
			@Parameter(description = "Credenciales de usuario para iniciar sesión", required = true, schema = @Schema(implementation = UsuarioDTO.class), examples = @ExampleObject(value = """
					    {
					      "username": "usuario",
					      "password": "1234567890"
					    }
					""")) @RequestBody UsuarioDTO loginRequest) {
		try {
			UsuarioDTO found = userService.getByUsername(loginRequest.getNombreUsuario());
			System.out.println(found.toString());

			if (found != null) {
				if (found.isVerified()) {
					System.out.println("entró");

					Authentication authentication = authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNombreUsuario(),
									loginRequest.getClave()));

					UserDetails userDetails = (UserDetails) authentication.getPrincipal();
					String jwt = jwtUtil.generateToken(userDetails);

					// Obtener el rol de userDetails si es nuestra clase User
					String role = "";
					if (userDetails instanceof Usuario) {
						System.out.println("entró");
						Usuario user = (Usuario) userDetails;
						role = user.getRole().name();
						System.out.println(role);
					}

					return ResponseEntity.ok(new AuthResponse(jwt, role));
				} else {

					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
							"Nombre de usuario o contraseña inválidos, usuario no encontrado o cuenta no verificada");
				}

			} else {

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("Nombre de usuario o contraseña inválidos, usuario no encontrado o cuenta no verificada");
			}

		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Nombre de usuario o contraseña inválidos, usuario no encontrado o cuenta no verificada");
		}
	}

	/**
	 * Maneja las solicitudes de registro de nuevos usuarios. Verifica si el nombre
	 * de usuario ya existe y crea un nuevo usuario si está disponible.
	 *
	 * @param registerRequest DTO con la información del nuevo usuario
	 * @return ResponseEntity con un mensaje de éxito si el registro es exitoso, o
	 *         un mensaje de error si falla
	 */
	@Operation(summary = "Registrar un nuevo usuario", description = """
			    Este endpoint permite crear una nueva cuenta de usuario en el sistema.

			    **¿Qué hace?** Registra un nuevo usuario con el nombre de usuario y contraseña proporcionados.
			    Por defecto, los usuarios creados mediante este endpoint tendrán el rol USUARIO.

			    **Paso a paso:**

			    1. Envía el nombre de usuario y contraseña deseados en formato JSON
			    2. El sistema verificará si el nombre de usuario ya existe
			    3. Si el nombre está disponible, se creará la cuenta y recibirás un mensaje de éxito
			    4. Después de registrarte, puedes usar el endpoint de login para obtener un token JWT

			    **Requisitos para la contraseña:** Requisitos básicos de una contraseña

			    **Nota:** Este endpoint es público y no requiere autenticación.
			""")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "User registered successfully"))),
			@ApiResponse(responseCode = "409", description = "El nombre de usuario ya existe", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Username already exists"))),
			@ApiResponse(responseCode = "409", description = "El email ya tiene una cuenta asociada", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Email already used"))),
			@ApiResponse(responseCode = "400", description = "Error al registrar el usuario", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Error registering user"))) })
	@PostMapping("/register")
	public ResponseEntity<?> register(
			@Parameter(description = "Información del nuevo usuario", required = true, schema = @Schema(implementation = UsuarioDTO.class), examples = @ExampleObject(value = """
										    {
					  "id": 0,
					  "nombre": "string",
					  "nombreUsuario": "string",
					  "email": "string",
					  "clave": "string",
					  "role": "USUARIO",
					  "verified": false
					}
										""")) @RequestBody UsuarioDTO registerRequest) {
		// Verificar si el nombre de usuario ya existe
		if (userService.findUsernameAlreadyTaken(registerRequest.getNombreUsuario())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
		}
		if (userService.findEmailAlreadyTaken(registerRequest.getCorreo())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Este correo ya tiene una cuenta asociada");
		}

		// Crear nuevo usuario
		int result = userService.create(registerRequest);
		if (result == 0) {
			UsuarioDTO usuarioRegistrado = userService.getByCorreo(registerRequest.getCorreo());
			try {
				emailService.enviarEmailDeVerificacion(usuarioRegistrado.getCorreo());
				System.out.println("Se envió el correo de verificación");
				return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
			} catch (Exception e) {
				System.err.println("Error al enviar el correo de verificación: " + e.getMessage());
				// Aunque el correo no se envíe, el usuario se creó correctamente
				return ResponseEntity.status(HttpStatus.CREATED)
						.body("Usuario registrado exitosamente, pero no se pudo enviar el correo de verificación");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar el usuario");
		}
	}

	/**
	 * Clase interna para representar la respuesta de autenticación. Contiene el
	 * token JWT y el rol del usuario autenticado.
	 */
	private static class AuthResponse {
		/** Token JWT generado para el usuario autenticado. */
		private final String token;

		/** Rol del usuario autenticado. */
		private final String role;

		/**
		 * Constructor con solo token.
		 *
		 * @param token Token JWT generado
		 */
		public AuthResponse(String token) {
			this.token = token;
			// Extraer rol del token
			this.role = null; // Se establecerá en el constructor con el parámetro de rol
		}

		/**
		 * Constructor con token y rol.
		 *
		 * @param token Token JWT generado
		 * @param role  Rol del usuario
		 */
		public AuthResponse(String token, String role) {
			this.token = token;
			this.role = role;
		}

		/**
		 * Obtiene el token JWT.
		 *
		 * @return Token JWT
		 */
		public String getToken() {
			return token;
		}

		/**
		 * Obtiene el rol del usuario.
		 *
		 * @return Rol del usuario
		 */
		public String getRole() {
			return role;
		}
	}

	
	/**
	 * Verifica la cuenta de un usuario a partir del correo recibido por email.
	 *
	 * @param correo Correo del usuario a verificar
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Verificar cuenta de usuario")
	@GetMapping("/verificar")
	public ResponseEntity<String> verificar(@RequestParam String correo) {
	    Optional<UsuarioDTO> found = Optional.ofNullable(userService.getByCorreo(correo));

	    if (found.isPresent()) {
	        UsuarioDTO usuario = found.get();

	        if (usuario.getIsVerified()) {
	            return new ResponseEntity<>("La cuenta ya estaba verificada", HttpStatus.OK);
	        }

	        usuario.setIsVerified(true);
	        usuario.setVerified(true);
	        userService.updateVerificationStatus(correo, usuario);

	        return new ResponseEntity<>("Cuenta verificada correctamente", HttpStatus.OK);
	    }

	    return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	}
	

}
