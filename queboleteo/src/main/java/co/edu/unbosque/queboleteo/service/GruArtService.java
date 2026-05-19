package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.GruArtDTO;
import co.edu.unbosque.queboleteo.entity.ArtistaIndividual;
import co.edu.unbosque.queboleteo.entity.GruArt;
import co.edu.unbosque.queboleteo.entity.GruArtId;
import co.edu.unbosque.queboleteo.entity.Grupo;
import co.edu.unbosque.queboleteo.repository.ArtistaIndividualRepository;
import co.edu.unbosque.queboleteo.repository.GruArtRepository;
import co.edu.unbosque.queboleteo.repository.GrupoRepository;

@Service
public class GruArtService {

	@Autowired
	private GruArtRepository gruArtRepo;

	@Autowired
	private GrupoRepository grupoRepo;

	@Autowired
	private ArtistaIndividualRepository artistaRepo;

	public GruArtService() {
	}

	/**
	 * Convierte una entidad a DTO.
	 * 
	 * @param entity Entidad GruArt
	 * @return DTO de la asociación
	 */
	private GruArtDTO toDTO(GruArt entity) {
		GruArtDTO dto = new GruArtDTO();
		dto.setIdGrupo(entity.getGrupo().getIdGrupo());
		dto.setIdArtista(entity.getArtista().getIdArtista());
		dto.setRol(entity.getRol());
		return dto;
	}

	/**
	 * Crea una nueva asociación grupo-artista con rol.
	 *
	 * @param dto DTO con idGrupo, idArtista y rol
	 * @return 0 si fue exitoso, 1 si grupo o artista no existen, 2 si la asociación
	 *         ya existe
	 */
	public int create(GruArtDTO dto) {
		if (dto == null || dto.getIdGrupo() == null || dto.getIdArtista() == null) {
			return 4;
		}
		
		Optional<Grupo> grupo = grupoRepo.findById(dto.getIdGrupo());
		Optional<ArtistaIndividual> artista = artistaRepo.findById(dto.getIdArtista());
		

		if (grupo.isEmpty() || artista.isEmpty()) {
			return 1;
		}

		GruArtId id = new GruArtId(dto.getIdGrupo(), dto.getIdArtista());
		if (gruArtRepo.existsById(id)) {
			return 2;
		}

		gruArtRepo.save(new GruArt(grupo.get(), artista.get(), dto.getRol()));
		return 0;
	}

	/**
	 * Obtiene todas las asociaciones grupo-artista.
	 *
	 * @return Lista de DTOs
	 */
	public List<GruArtDTO> getAll() {
		List<GruArt> entityList = gruArtRepo.findAll();
		List<GruArtDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los artistas de un grupo específico.
	 * 
	 * @param idGrupo
	 * @return
	 */
	public List<GruArtDTO> getByGrupoId(Long idGrupo) {
		Optional<Grupo> grupo = grupoRepo.findById(idGrupo);
		if (grupo.isEmpty())
			return new ArrayList<>();
		List<GruArt> entityList = gruArtRepo.findByGrupo(grupo.get());
		List<GruArtDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los grupos de un artista específico.
	 * 
	 * @param idArtista
	 * @return
	 */
	public List<GruArtDTO> getByArtistaId(Long idArtista) {
		Optional<ArtistaIndividual> artista = artistaRepo.findById(idArtista);
		if (artista.isEmpty())
			return new ArrayList<>();
		List<GruArt> entityList = gruArtRepo.findByArtista(artista.get());
		List<GruArtDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todas las asociaciones con un rol específico.
	 *
	 * @param rol Rol a buscar
	 * @return Lista de DTOs con ese rol
	 */
	public List<GruArtDTO> getByRol(String rol) {
		List<GruArt> entityList = gruArtRepo.findByRol(rol);
		List<GruArtDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Actualiza el rol de un artista en un grupo.
	 *
	 * @param idGrupo   ID del grupo
	 * @param idArtista ID del artista
	 * @param newData   DTO con el nuevo rol
	 * @return 0 si fue actualizado correctamente, 1 si no existe
	 */
	public int updateByIds(Long idGrupo, Long idArtista, GruArtDTO newData) {
		GruArtId id = new GruArtId(idGrupo, idArtista);
		Optional<GruArt> found = gruArtRepo.findById(id);
		if (found.isPresent()) {
			GruArt entity = found.get();
			entity.setRol(newData.getRol());
			gruArtRepo.save(entity);
			return 0;
		}
		return 1;
	}

	/**
	 * Elimina una asociación grupo-artista por sus IDs.
	 *
	 * @param idGrupo   ID del grupo
	 * @param idArtista ID del artista
	 * @return 0 si fue eliminada correctamente, 1 si no existe
	 */
	public int deleteByIds(Long idGrupo, Long idArtista) {
		GruArtId id = new GruArtId(idGrupo, idArtista);
		if (gruArtRepo.existsById(id)) {
			gruArtRepo.deleteById(id);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de asociaciones grupo-artista.
	 *
	 * @return Cantidad total de asociaciones
	 */
	public Long count() {
		return gruArtRepo.count();
	}

}
