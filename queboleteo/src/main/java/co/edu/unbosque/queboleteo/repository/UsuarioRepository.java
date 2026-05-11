package co.edu.unbosque.queboleteo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	/**
	 * Busca un usuario por su nombre de usuario
	 * 
	 * @param nombreUsuario nombre de usuario a buscar
	 * @return Optional con el usuario encontrado
	 */
	public Optional<Usuario> findByNombreUsuario(String nombreUsuario);

	/**
	 * Busca un usuario por su correo
	 * 
	 * @param correo correo del usuario
	 * @return Optional con el usuario encontrado
	 */
	public Optional<Usuario> findByCorreo(String correo);

	/**
	 * Busca un usuario por documento de identidad
	 * 
	 * @param documentoIdentidad documento del usuario
	 * @return Optional con el usuario encontrado
	 */
	public Optional<Usuario> findByDocumentoIdentidad(String documentoIdentidad);

	/**
	 * Elimina un usuario por correo
	 * 
	 * @param correo correo del usuario
	 */
	public void deleteByCorreo(String correo);

	/**
	 * Elimina un usuario por nombre de usuario
	 * 
	 * @param nombreUsuario nombre de usuario
	 */
	public void deleteByNombreUsuario(String nombreUsuario);

}