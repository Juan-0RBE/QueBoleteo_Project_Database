package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.UsuarioDTO;
import co.edu.unbosque.queboleteo.entity.Usuario;
import co.edu.unbosque.queboleteo.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioService() {
	}

	/**
	 * Crear usuario
	 * 
	 * Retornos:
	 * 0 = éxito
	 * 1 = username ya existe
	 * 2 = contraseña inválida
	 * 3 = correo ya existe
	 */
	@Override
	public int create(UsuarioDTO newData) {

		Usuario entity = modelMapper.map(newData, Usuario.class);

		if (findUsernameAlreadyTaken(entity.getNombreUsuario())) {
			return 1;
		}

		if (!newData.getClave()
				.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$")) {
			return 2;
		}

		if (findEmailAlreadyTaken(entity.getCorreo())) {
			return 3;
		}

		entity.setClave(passwordEncoder.encode(entity.getPassword()));

		if (newData.getRole() != null) {
			entity.setRole(newData.getRole());
		}

		usuarioRepo.save(entity);

		return 0;
	}

	/**
	 * Obtener todos los usuarios
	 */
	@Override
	public List<UsuarioDTO> getAll() {

		List<Usuario> entityList = usuarioRepo.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			UsuarioDTO dto = modelMapper.map(entity, UsuarioDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	/**
	 * Eliminar usuario por correo
	 * 
	 * Como el ID es String (correo), este método del CRUDOperation no aplica.
	 */
	@Override
	public int deleteById(Long id) {
		return 1;
	}

	/**
	 * Eliminar usuario por correo
	 */
	public int deleteByCorreo(String correo) {

		Optional<Usuario> found = usuarioRepo.findById(correo);

		if (found.isPresent()) {
			usuarioRepo.delete(found.get());
			return 0;
		}

		return 1;
	}

	/**
	 * Eliminar usuario por username
	 */
	public int deleteByUsername(String username) {

		Optional<Usuario> found = usuarioRepo.findByNombreUsuario(username);

		if (found.isPresent()) {
			usuarioRepo.delete(found.get());
			return 0;
		}

		return 1;
	}

	/**
	 * Actualizar usuario
	 * 
	 * Retornos:
	 * 0 = éxito
	 * 1 = username ya existe
	 * 2 = usuario no encontrado
	 */
	@Override
	public int updateById(Long id, UsuarioDTO newData) {
		return 1;
	}

	/**
	 * Actualizar usuario por correo
	 */
	public int updateByCorreo(String correo, UsuarioDTO newData) {

		Optional<Usuario> found = usuarioRepo.findById(correo);

		if (!found.isPresent()) {
			return 2;
		}

		Optional<Usuario> usernameFound = usuarioRepo.findByNombreUsuario(newData.getNombreUsuario());

		if (usernameFound.isPresent()
				&& !usernameFound.get().getCorreo().equals(correo)) {
			return 1;
		}

		Usuario temp = found.get();

		temp.setNombreUsuario(newData.getNombreUsuario());
		temp.setDocumentoIdentidad(newData.getDocumentoIdentidad());

		temp.setPrimerNombre(newData.getPrimerNombre());
		temp.setSegundoNombre(newData.getSegundoNombre());

		temp.setPrimerApellido(newData.getPrimerApellido());
		temp.setSegundoApellido(newData.getSegundoApellido());

		temp.setFechaNacimiento(newData.getFechaNacimiento());
		temp.setEdad(newData.getEdad());

		temp.setNumeroTelefono(newData.getNumeroTelefono());

		if (newData.getRole() != null) {
			temp.setRole(newData.getRole());
		}

		usuarioRepo.save(temp);

		return 0;
	}

	/**
	 * Contar usuarios
	 */
	@Override
	public Long count() {
		return usuarioRepo.count();
	}

	/**
	 * Verificar existencia por ID Long
	 * 
	 * No aplica en este caso
	 */
	@Override
	public boolean exist(Long id) {
		return false;
	}

	/**
	 * Verificar existencia por correo
	 */
	public boolean existByCorreo(String correo) {
		return usuarioRepo.existsById(correo);
	}

	/**
	 * Verificar username repetido
	 */
	public boolean findUsernameAlreadyTaken(String username) {

		Optional<Usuario> found = usuarioRepo.findByNombreUsuario(username);

		return found.isPresent();
	}

	/**
	 * Verificar correo repetido
	 */
	public boolean findEmailAlreadyTaken(String correo) {

		Optional<Usuario> found = usuarioRepo.findByCorreo(correo);

		return found.isPresent();
	}

	/**
	 * Obtener usuario por correo
	 */
	public UsuarioDTO getByCorreo(String correo) {

		Optional<Usuario> found = usuarioRepo.findById(correo);

		if (found.isPresent()) {
			return modelMapper.map(found.get(), UsuarioDTO.class);
		}

		return null;
	}

	/**
	 * Obtener usuario por username
	 */
	public UsuarioDTO getByUsername(String username) {

		Optional<Usuario> found = usuarioRepo.findByNombreUsuario(username);

		if (found.isPresent()) {
			return modelMapper.map(found.get(), UsuarioDTO.class);
		}

		return null;
	}

	/**
	 * Validar credenciales
	 * 
	 * Retornos:
	 * 0 = credenciales válidas
	 * 1 = inválidas
	 */
	public int validateCredentials(String correo, String password) {

		Optional<Usuario> userOpt = usuarioRepo.findByCorreo(correo);

		if (userOpt.isPresent()) {

			Usuario user = userOpt.get();

			if (passwordEncoder.matches(password, user.getPassword())) {
				return 0;
			}
		}

		return 1;
	}

	/**
	 * Actualizar contraseña
	 * 
	 * Retornos:
	 * 0 = éxito
	 * 1 = usuario no encontrado
	 * 2 = contraseña inválida
	 */
	public int updatePasswordByCorreo(String correo, UsuarioDTO newData) {

		Optional<Usuario> prevEntity = usuarioRepo.findById(correo);

		if (!prevEntity.isPresent()) {
			return 1;
		}

		if (!newData.getClave()
				.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$")) {
			return 2;
		}

		Usuario temp = prevEntity.get();

		temp.setClave(passwordEncoder.encode(newData.getClave()));

		usuarioRepo.save(temp);

		return 0;
	}

	/**
	 * Actualizar verificación
	 * 
	 * Retornos:
	 * 0 = éxito
	 * 1 = usuario no encontrado
	 */
	public int updateVerificationStatus(String correo, UsuarioDTO newData) {

		Optional<Usuario> prevEntity = usuarioRepo.findById(correo);

		if (prevEntity.isPresent()) {

			Usuario temp = prevEntity.get();

			temp.setIsVerified(newData.getIsVerified());

			usuarioRepo.save(temp);

			return 0;
		}

		return 1;
	}

	@Override
	public UsuarioDTO getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}