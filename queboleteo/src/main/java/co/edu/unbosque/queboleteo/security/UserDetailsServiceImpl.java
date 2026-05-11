package co.edu.unbosque.queboleteo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.repository.UsuarioRepository;

/**
 * Servicio para cargar los usuarios desde la base de datos.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsuarioRepository userRepository;

	public UserDetailsServiceImpl(UsuarioRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByNombreUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
	}
}
