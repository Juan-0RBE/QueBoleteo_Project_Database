package co.edu.unbosque.queboleteo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración principal de seguridad con soporte JWT. Define las rutas
 * públicas, privadas y roles permitidos.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Cadena principal de filtros de seguridad de Spring Security.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				// Desactiva CSRF
				.csrf(csrf -> csrf.disable())

				// Permite CORS
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))

				// Permitir acceso total a todos los endpoints
				.authorizeHttpRequests(auth -> auth
						.anyRequest().permitAll())

				// Stateless
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	/**
	 * Proveedor de autenticación basado en nuestra clase UserDetailsService.
	 */
	/**
	 * Proveedor de autenticación basado en nuestra clase UserDetailsService.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {

	    DaoAuthenticationProvider authProvider =
	            new DaoAuthenticationProvider(userDetailsService);

	    authProvider.setPasswordEncoder(passwordEncoder());

	    return authProvider;
	}

	/**
	 * Expone el AuthenticationManager del contexto de Spring.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * Codificador de contraseñas seguro (BCrypt).
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configura CORS para permitir peticiones desde el frontend (Angular).
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cfg = new CorsConfiguration();

		// URL de tu frontend
		cfg.setAllowedOrigins(List.of("http://localhost:4200"));

		// Métodos permitidos
		cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// Headers permitidos (incluye Authorization)
		cfg.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));

		// Permitir credenciales (si usas cookies o headers personalizados)
		cfg.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
		src.registerCorsConfiguration("/**", cfg);
		return src;
	}
}
