package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ArtGenDTO;
import co.edu.unbosque.queboleteo.entity.ArtGen;
import co.edu.unbosque.queboleteo.entity.ArtGenId;
import co.edu.unbosque.queboleteo.entity.ArtistaIndividual;
import co.edu.unbosque.queboleteo.entity.Genero;
import co.edu.unbosque.queboleteo.repository.ArtGenRepository;
import co.edu.unbosque.queboleteo.repository.ArtistaIndividualRepository;
import co.edu.unbosque.queboleteo.repository.GeneroRepository;

@Service
public class ArtGenService {

	@Autowired
	private ArtGenRepository artGenRepo;

	@Autowired
	private ArtistaIndividualRepository artistaRepo;

	@Autowired
	private GeneroRepository generoRepo;

	public ArtGenService() {
	}

	/**
	 * Convierte una entidad a DTO.
	 *
	 * @param entity Entidad ArtGen
	 * @return DTO de la asociación
	 */
	private ArtGenDTO toDTO(ArtGen entity) {
		ArtGenDTO dto = new ArtGenDTO();
		dto.setIdArtista(entity.getArtista().getIdArtista());
		dto.setIdGenero(entity.getGenero().getIdGenero());
		return dto;
	}

	/**
	 * Crea una nueva asociación artista-género.
	 *
	 * @param dto DTO con idArtista e idGenero
	 * @return 0 si fue exitoso, 1 si artista o género no existen, 2 si la
	 *         asociación ya existe
	 */
	public int create(ArtGenDTO dto) {
		Optional<ArtistaIndividual> artista = artistaRepo.findById(dto.getIdArtista());
		Optional<Genero> genero = generoRepo.findById(dto.getIdGenero());

		if (artista.isEmpty() || genero.isEmpty())
			return 1;

		ArtGenId id = new ArtGenId(dto.getIdArtista(), dto.getIdGenero());
		if (artGenRepo.existsById(id))
			return 2;

		artGenRepo.save(new ArtGen(artista.get(), genero.get()));
		return 0;
	}

	/**
	 * Obtiene todas las asociaciones artista-género.
	 *
	 * @return Lista de DTOs
	 */
	public List<ArtGenDTO> getAll() {
		List<ArtGen> entityList = artGenRepo.findAll();
		List<ArtGenDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los géneros de un artista específico.
	 *
	 * @param idArtista ID del artista
	 * @return Lista de DTOs filtrada por artista
	 */
	public List<ArtGenDTO> getByArtistaId(Long idArtista) {
		Optional<ArtistaIndividual> artista = artistaRepo.findById(idArtista);
		if (artista.isEmpty())
			return new ArrayList<>();
		List<ArtGen> entityList = artGenRepo.findByArtista(artista.get());
		List<ArtGenDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los artistas de un género específico.
	 *
	 * @param idGenero ID del género
	 * @return Lista de DTOs filtrada por género
	 */
	public List<ArtGenDTO> getByGeneroId(Long idGenero) {
		Optional<Genero> genero = generoRepo.findById(idGenero);
		if (genero.isEmpty())
			return new ArrayList<>();
		List<ArtGen> entityList = artGenRepo.findByGenero(genero.get());
		List<ArtGenDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Elimina una asociación artista-género por sus IDs.
	 *
	 * @param idArtista ID del artista
	 * @param idGenero  ID del género
	 * @return 0 si fue eliminada correctamente, 1 si no existe
	 */
	public int deleteByIds(Long idArtista, Long idGenero) {
		ArtGenId id = new ArtGenId(idArtista, idGenero);
		if (artGenRepo.existsById(id)) {
			artGenRepo.deleteById(id);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de asociaciones artista-género.
	 *
	 * @return Cantidad total de asociaciones
	 */
	public Long count() {
		return artGenRepo.count();
	}
}