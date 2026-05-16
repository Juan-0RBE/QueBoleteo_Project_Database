package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.OrganizadorDTO;
import co.edu.unbosque.queboleteo.entity.Organizador;
import co.edu.unbosque.queboleteo.repository.OrganizadorRepository;

@Service
public class OrganizadorService {

	@Autowired
	private OrganizadorRepository organizadorRepo;

	/**
	 * Crea un nuevo organizador.
	 * 
	 * @param dto datos del organizador
	 * @return 0 si se creó correctamente, 1 si ya existe, 2 si el correo no cumple con el formato estándar
	 */
	public int create(OrganizadorDTO dto) {

		Organizador found = organizadorRepo.findById(dto.getNombreOrganizador()).orElse(null);

		if (found != null) {
			return 1;
		} else if (!dto.getCorreoOrganizador().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
			return 2;
		}
		Organizador entity = dtoToEntity(dto);

		organizadorRepo.save(entity);

		return 0;
	}

	/**
	 * Obtiene todos los organizadores.
	 * 
	 * @return Lista de organizadores
	 */
	public List<OrganizadorDTO> getAll() {

		List<Organizador> entities = organizadorRepo.findAll();

		List<OrganizadorDTO> dtos = new ArrayList<>();

		for (Organizador entity : entities) {

			dtos.add(entityToDTO(entity));
		}

		return dtos;
	}

	/**
	 * Obtiene un organizador por nombre.
	 * 
	 * @param nombre nombre del organizador
	 * @return DTO del organizador o null
	 */
	public OrganizadorDTO getById(String nombre) {

		Organizador entity = organizadorRepo.findById(nombre).orElse(null);

		if (entity == null) {
			return null;
		}

		return entityToDTO(entity);
	}

	/**
	 * Actualiza un organizador existente.
	 * 
	 * @param nombre nombre del organizador
	 * @param dto    nuevos datos
	 * @return 0 si se actualizó, 1 si no existe
	 */
	public int updateById(String nombre, OrganizadorDTO dto) {

		Organizador found = organizadorRepo.findById(nombre).orElse(null);

		if (found == null) {
			return 1;
		}

		found.setCorreoOrganizador(dto.getCorreoOrganizador());

		found.setLogo(dto.getLogo());

		organizadorRepo.save(found);

		return 0;
	}

	/**
	 * Elimina un organizador por nombre.
	 * 
	 * @param nombre nombre del organizador
	 * @return 0 si se eliminó, 1 si no existe
	 */
	public int deleteById(String nombre) {

		Organizador found = organizadorRepo.findById(nombre).orElse(null);

		if (found == null) {
			return 1;
		}

		organizadorRepo.delete(found);

		return 0;
	}

	/**
	 * Cuenta el total de organizadores.
	 * 
	 * @return cantidad de organizadores
	 */
	public long count() {

		return organizadorRepo.count();
	}

	/**
	 * Convierte un DTO en entidad.
	 * 
	 * @param dto DTO a convertir
	 * @return entidad Organizador
	 */
	private Organizador dtoToEntity(OrganizadorDTO dto) {

		Organizador entity = new Organizador();

		entity.setNombreOrganizador(dto.getNombreOrganizador());

		entity.setCorreoOrganizador(dto.getCorreoOrganizador());

		entity.setLogo(dto.getLogo());

		return entity;
	}

	/**
	 * Convierte una entidad en DTO.
	 * 
	 * @param entity entidad a convertir
	 * @return DTO Organizador
	 */
	private OrganizadorDTO entityToDTO(Organizador entity) {

		OrganizadorDTO dto = new OrganizadorDTO();

		dto.setNombreOrganizador(entity.getNombreOrganizador());

		dto.setCorreoOrganizador(entity.getCorreoOrganizador());

		dto.setLogo(entity.getLogo());

		return dto;
	}

}