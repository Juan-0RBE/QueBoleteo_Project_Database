package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.GrupoDTO;
import co.edu.unbosque.queboleteo.entity.Grupo;
import co.edu.unbosque.queboleteo.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepo;

	/**
	 * Crea un nuevo grupo.
	 * 
	 * @param dto datos del grupo
	 * @return 0 si se creó correctamente, 1 si ya existe
	 */
	public int create(GrupoDTO dto) {

		Grupo found = grupoRepo.findByNombreGrupo(
				dto.getNombreGrupo());

		if (found != null) {
			return 1;
		}

		Grupo entity = dtoToEntity(dto);

		grupoRepo.save(entity);

		return 0;
	}

	/**
	 * Obtiene todos los grupos.
	 * 
	 * @return Lista de grupos
	 */
	public List<GrupoDTO> getAll() {

		List<Grupo> entities = grupoRepo.findAll();

		List<GrupoDTO> dtos = new ArrayList<>();

		for (Grupo entity : entities) {

			dtos.add(entityToDTO(entity));
		}

		return dtos;
	}

	/**
	 * Obtiene un grupo por ID.
	 * 
	 * @param id ID del grupo
	 * @return DTO del grupo o null
	 */
	public GrupoDTO getById(Long id) {

		Grupo entity = grupoRepo.findById(id)
				.orElse(null);

		if (entity == null) {
			return null;
		}

		return entityToDTO(entity);
	}

	/**
	 * Actualiza un grupo existente.
	 * 
	 * @param id ID del grupo
	 * @param dto nuevos datos
	 * @return 0 si se actualizó, 1 si no existe
	 */
	public int updateById(Long id, GrupoDTO dto) {

		Grupo found = grupoRepo.findById(id)
				.orElse(null);

		if (found == null) {
			return 1;
		}

		found.setNombreGrupo(dto.getNombreGrupo());
		found.setDescripcionGrupo(dto.getDescripcionGrupo());
		found.setImagenGrupo(dto.getImagenGrupo());
		found.setPaisOrigenGrupo(dto.getPaisOrigenGrupo());
		found.setTiempoDuracion(dto.getTiempoDuracion());
		found.setLenguajeGrupo(dto.getLenguajeGrupo());

		grupoRepo.save(found);

		return 0;
	}

	/**
	 * Elimina un grupo por ID.
	 * 
	 * @param id ID del grupo
	 * @return 0 si se eliminó, 1 si no existe
	 */
	public int deleteById(Long id) {

		Grupo found = grupoRepo.findById(id)
				.orElse(null);

		if (found == null) {
			return 1;
		}

		grupoRepo.delete(found);

		return 0;
	}

	/**
	 * Cuenta el total de grupos.
	 * 
	 * @return cantidad de grupos
	 */
	public long count() {

		return grupoRepo.count();
	}

	/**
	 * Convierte un DTO en entidad.
	 * 
	 * @param dto DTO a convertir
	 * @return entidad Grupo
	 */
	private Grupo dtoToEntity(GrupoDTO dto) {

		Grupo entity = new Grupo();

		entity.setIdGrupo(dto.getIdGrupo());
		entity.setNombreGrupo(dto.getNombreGrupo());
		entity.setDescripcionGrupo(dto.getDescripcionGrupo());
		entity.setImagenGrupo(dto.getImagenGrupo());
		entity.setPaisOrigenGrupo(dto.getPaisOrigenGrupo());
		entity.setTiempoDuracion(dto.getTiempoDuracion());
		entity.setLenguajeGrupo(dto.getLenguajeGrupo());

		return entity;
	}

	/**
	 * Convierte una entidad en DTO.
	 * 
	 * @param entity entidad a convertir
	 * @return DTO Grupo
	 */
	private GrupoDTO entityToDTO(Grupo entity) {

		GrupoDTO dto = new GrupoDTO();

		dto.setIdGrupo(entity.getIdGrupo());
		dto.setNombreGrupo(entity.getNombreGrupo());
		dto.setDescripcionGrupo(entity.getDescripcionGrupo());
		dto.setImagenGrupo(entity.getImagenGrupo());
		dto.setPaisOrigenGrupo(entity.getPaisOrigenGrupo());
		dto.setTiempoDuracion(entity.getTiempoDuracion());
		dto.setLenguajeGrupo(entity.getLenguajeGrupo());

		return dto;
	}

}