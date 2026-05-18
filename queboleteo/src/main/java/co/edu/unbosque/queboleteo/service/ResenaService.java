package co.edu.unbosque.queboleteo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ResenaDTO;
import co.edu.unbosque.queboleteo.entity.Boleto;
import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.Resena;
import co.edu.unbosque.queboleteo.entity.Usuario;
import co.edu.unbosque.queboleteo.repository.BoletoRepository;
import co.edu.unbosque.queboleteo.repository.ConciertoRepository;
import co.edu.unbosque.queboleteo.repository.ResenaRepository;
import co.edu.unbosque.queboleteo.repository.UsuarioRepository;

@Service
public class ResenaService implements CRUDOperation<ResenaDTO> {

	@Autowired
	private ResenaRepository resenaRepo;

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private ConciertoRepository conciertoRepo;

	@Autowired
	private BoletoRepository boletoRepo;

	public ResenaService() {
	}

	/**
	 * Convierte un DTO a entidad. Resuelve las relaciones con Usuario (correo) y
	 * Concierto (idConcierto).
	 *
	 * @param dto DTO de la reseña
	 * @return Entidad Resena lista para persistir
	 */
	private Resena toEntity(ResenaDTO dto) {
		Resena entity = new Resena();
		entity.setComentario(dto.getComentario());
		entity.setCalificacion(dto.getCalificacion());

		if (dto.getCorreoUsuario() != null) {
			Optional<Usuario> usuario = usuarioRepo.findById(dto.getCorreoUsuario());
			usuario.ifPresent(entity::setUsuario);
		}

		if (dto.getIdConcierto() != null) {
			Optional<Concierto> concierto = conciertoRepo.findById(dto.getIdConcierto());
			concierto.ifPresent(entity::setConcierto);
		}

		return entity;
	}

	/**
	 * Convierte una entidad a DTO. Extrae solo el correo del usuario y el ID del
	 * concierto.
	 *
	 * @param entity Entidad Resena
	 * @return DTO de la reseña
	 */
	private ResenaDTO toDTO(Resena entity) {
		ResenaDTO dto = new ResenaDTO();
		dto.setIdResena(entity.getIdResena());
		dto.setComentario(entity.getComentario());
		dto.setCalificacion(entity.getCalificacion());

		if (entity.getUsuario() != null) {
			dto.setCorreoUsuario(entity.getUsuario().getCorreo());
		}

		if (entity.getConcierto() != null) {
			dto.setIdConcierto(entity.getConcierto().getIdConcierto());
		}

		return dto;
	}

	/**
	 * Crea una nueva reseña. Un usuario solo puede dejar una reseña por concierto.
	 *
	 * @param newData DTO con los datos de la reseña
	 * @return 0 si fue exitoso, 1 si ese usuario ya reseñó ese concierto, 2 si no
	 *         ha comprado un boleto
	 */
	@Override
	public int create(ResenaDTO newData) {
		if (newData.getCorreoUsuario() != null && newData.getIdConcierto() != null) {
			Optional<Usuario> usuario = usuarioRepo.findById(newData.getCorreoUsuario());
			Optional<Concierto> concierto = conciertoRepo.findById(newData.getIdConcierto());

			if (usuario.isPresent() && concierto.isPresent()) {
				boolean comproBoleto = boletoRepo.existsByUsuarioAndConcierto(newData.getCorreoUsuario(),
						newData.getIdConcierto());
				if (!comproBoleto) {
					return 2;
				}
				if (concierto.isPresent()) {
					LocalDateTime ahora = LocalDateTime.now();
					if (concierto.get().getFechaConcierto().isAfter(ahora)) {
						return 3;
					}
				}

				Optional<Resena> found = resenaRepo.findByUsuarioAndConcierto(usuario.get(), concierto.get());
				if (found.isPresent())
					return 1;
			}
		}

		resenaRepo.save(toEntity(newData));
		return 0;
	}

	/**
	 * Obtiene todas las reseñas registradas.
	 *
	 * @return Lista de DTOs de reseñas
	 */
	@Override
	public List<ResenaDTO> getAll() {
		List<Resena> entityList = resenaRepo.findAll();
		List<ResenaDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene una reseña por su ID.
	 *
	 * @param id ID de la reseña
	 * @return DTO de la reseña si existe, null en caso contrario
	 */
	@Override
	public ResenaDTO getById(Long id) {
		Optional<Resena> found = resenaRepo.findById(id);
		if (found.isPresent()) {
			return toDTO(found.get());
		}
		return null;
	}

	/**
	 * Obtiene todas las reseñas registradas de un concierto.
	 *
	 * @return Lista de DTOs de reseñas
	 */
	public List<ResenaDTO> getAllByConcierto(String nombreConcierto) {
		List<Resena> entityList = resenaRepo
				.findByConcierto(conciertoRepo.findByNombreConcierto(nombreConcierto).get());
		List<ResenaDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Elimina una reseña por su ID.
	 *
	 * @param id ID de la reseña
	 * @return 0 si fue eliminada correctamente, 1 si no existe
	 */
	@Override
	public int deleteById(Long id) {
		Optional<Resena> found = resenaRepo.findById(id);
		if (found.isPresent()) {
			resenaRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza una reseña existente.
	 *
	 * @param id      ID de la reseña a actualizar
	 * @param newData Nuevos datos de la reseña
	 * @return 0 si actualizó correctamente, 1 si no existe
	 */
	@Override
	public int updateById(Long id, ResenaDTO newData) {
		Optional<Resena> found = resenaRepo.findById(id);
		if (found.isPresent()) {
			Resena entity = found.get();
			entity.setComentario(newData.getComentario());
			entity.setCalificacion(newData.getCalificacion());

			if (newData.getCorreoUsuario() != null) {
				Optional<Usuario> usuario = usuarioRepo.findById(newData.getCorreoUsuario());
				usuario.ifPresent(entity::setUsuario);
			}

			if (newData.getIdConcierto() != null) {
				Optional<Concierto> concierto = conciertoRepo.findById(newData.getIdConcierto());
				concierto.ifPresent(entity::setConcierto);
			}

			resenaRepo.save(entity);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de reseñas registradas.
	 *
	 * @return Cantidad total de reseñas
	 */
	@Override
	public Long count() {
		return resenaRepo.count();
	}

	/**
	 * Verifica si existe una reseña con el ID dado.
	 *
	 * @param id ID de la reseña
	 * @return true si existe, false en caso contrario
	 */
	@Override
	public boolean exist(Long id) {
		return resenaRepo.existsById(id);
	}
}