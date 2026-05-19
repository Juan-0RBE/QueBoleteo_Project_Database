package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.GruGenDTO;
import co.edu.unbosque.queboleteo.entity.Genero;
import co.edu.unbosque.queboleteo.entity.GruGen;
import co.edu.unbosque.queboleteo.entity.GruGenId;
import co.edu.unbosque.queboleteo.entity.Grupo;
import co.edu.unbosque.queboleteo.repository.GeneroRepository;
import co.edu.unbosque.queboleteo.repository.GruGenRepository;
import co.edu.unbosque.queboleteo.repository.GrupoRepository;

@Service
public class GruGenService {

	@Autowired
	private GruGenRepository gruGenRepo;

	@Autowired
	private GrupoRepository grupoRepo;

	@Autowired
	private GeneroRepository generoRepo;

	public GruGenService() {
	}

	/**
	 * Convierte una entidad a DTO.
	 *
	 * @param entity Entidad GruGen
	 * @return DTO de la asociación
	 */
	private GruGenDTO toDTO(GruGen entity) {
		GruGenDTO dto = new GruGenDTO();
		dto.setIdGrupo(entity.getGrupo().getIdGrupo());
		dto.setIdGenero(entity.getGenero().getIdGenero());
		return dto;
	}

	/**
	 * Crea una nueva asociación grupo género.
	 *
	 * @param dto DTO con idGrupo e idGenero
	 * @return 0 si fue exitoso, 1 si grupo o género no existen, 2 si la asociación
	 *         ya existe
	 */
	public int create(GruGenDTO dto) {
		Optional<Grupo> grupo = grupoRepo.findById(dto.getIdGrupo());
		Optional<Genero> genero = generoRepo.findById(dto.getIdGenero());

		if (grupo.isEmpty() || genero.isEmpty())
			return 1;

		GruGenId id = new GruGenId(dto.getIdGrupo(), dto.getIdGenero());
		if (gruGenRepo.existsById(id)) {
			return 2;			
		}

		gruGenRepo.save(new GruGen(grupo.get(), genero.get()));
		return 0;
	}

	/**
	 * Obtiene todas las asociaciones grupo-género.
	 *
	 * @return Lista de DTOs
	 */
	public List<GruGenDTO> getAll() {
		List<GruGen> entityList = gruGenRepo.findAll();
		List<GruGenDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los géneros de un grupo específico.
	 *
	 * @param idGrupo ID del grupo
	 * @return Lista de DTOs filtrada por grupo
	 */
	public List<GruGenDTO> getByGrupoId(Long idGrupo) {
		Optional<Grupo> grupo = grupoRepo.findById(idGrupo);
		if (grupo.isEmpty())
			return new ArrayList<>();
		List<GruGen> entityList = gruGenRepo.findByGrupo(grupo.get());
		List<GruGenDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los grupos de un género específico.
	 *
	 * @param idGenero ID del género
	 * @return Lista de DTOs filtrada por género
	 */
	public List<GruGenDTO> getByGeneroId(Long idGenero) {
		Optional<Genero> genero = generoRepo.findById(idGenero);
		if (genero.isEmpty())
			return new ArrayList<>();
		List<GruGen> entityList = gruGenRepo.findByGenero(genero.get());
		List<GruGenDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Elimina una asociación grupo-género por sus IDs.
	 *
	 * @param idGrupo  ID del grupo
	 * @param idGenero ID del género
	 * @return 0 si fue eliminada correctamente, 1 si no existe
	 */
	public int deleteByIds(Long idGrupo, Long idGenero) {
		GruGenId id = new GruGenId(idGrupo, idGenero);
		if (gruGenRepo.existsById(id)) {
			gruGenRepo.deleteById(id);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de asociaciones grupo-género.
	 *
	 * @return Cantidad total de asociaciones
	 */
	public Long count() {
		return gruGenRepo.count();
	}
}