package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ConArtDTO;
import co.edu.unbosque.queboleteo.entity.ArtistaIndividual;
import co.edu.unbosque.queboleteo.entity.ConArt;
import co.edu.unbosque.queboleteo.entity.ConArtId;
import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.repository.ArtistaIndividualRepository;
import co.edu.unbosque.queboleteo.repository.ConArtRepository;
import co.edu.unbosque.queboleteo.repository.ConciertoRepository;

@Service
public class ConArtService {

	@Autowired
	private ConArtRepository conArtRepo;

	@Autowired
	private ConciertoRepository conciertoRepo;

	@Autowired
	private ArtistaIndividualRepository artistaRepo;

	public ConArtService() {
	}
	
	/**
	 * Convierte una entidad a DTO.
	 *
	 * @param entity Entidad ConArt
	 * @return DTO de la asociación
	 */
	private ConArtDTO toDTO(ConArt entity) {
		ConArtDTO dto = new ConArtDTO();
		dto.setIdConcierto(entity.getConcierto().getIdConcierto());
		dto.setIdArtista(entity.getArtista().getIdArtista());
		return dto;
	}

	/**
	 * Crea una nueva asociación concierto artista.
	 *
	 * @param dto DTO con los IDs de concierto y artista
	 * @return 0 si fue exitoso, 1 si concierto o artista no existen, 2 si la
	 *         asociación ya existe
	 */
	public int create(ConArtDTO dto) {
		Optional<Concierto> concierto = conciertoRepo.findById(dto.getIdConcierto());
		Optional<ArtistaIndividual> artista = artistaRepo.findById(dto.getIdArtista());
		if (concierto.isEmpty() || artista.isEmpty()) {
			return 1;			
		}
		ConArtId id = new ConArtId(dto.getIdConcierto(), dto.getIdArtista());
		if (conArtRepo.existsById(id)) {
			return 2;			
		}
		conArtRepo.save(new ConArt(concierto.get(), artista.get()));
		return 0;
	}

	/**
	 * Obtiene todas las asociaciones concierto-artista.
	 *
	 * @return Lista de DTOs
	 */
	public List<ConArtDTO> getAll() {
		List<ConArt> entityList = conArtRepo.findAll();
		List<ConArtDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los artistas de un concierto específico.
	 *
	 * @param idConcierto ID del concierto
	 * @return Lista de DTOs filtrada por concierto
	 */
	public List<ConArtDTO> getByConciertoId(Long idConcierto) {
		Optional<Concierto> concierto = conciertoRepo.findById(idConcierto);
		if (concierto.isEmpty()) {
			return new ArrayList<>();			
		}
		List<ConArt> entityList = conArtRepo.findByConcierto(concierto.get());
		List<ConArtDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los conciertos de un artista específico.
	 *
	 * @param idArtista ID del artista
	 * @return Lista de DTOs filtrada por artista
	 */
	public List<ConArtDTO> getByArtistaId(Long idArtista) {
		Optional<ArtistaIndividual> artista = artistaRepo.findById(idArtista);
		if (artista.isEmpty()) {
			return new ArrayList<>();			
		}
		List<ConArt> entityList = conArtRepo.findByArtista(artista.get());
		List<ConArtDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Elimina una asociación concierto-artista por sus dos IDs.
	 *
	 * @param idConcierto ID del concierto
	 * @param idArtista   ID del artista
	 * @return 0 si fue eliminada correctamente, 1 si no existe
	 */
	public int deleteByIds(Long idConcierto, Long idArtista) {
		ConArtId id = new ConArtId(idConcierto, idArtista);
		if (conArtRepo.existsById(id)) {
			conArtRepo.deleteById(id);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de asociaciones concierto-artista.
	 *
	 * @return Cantidad total de asociaciones
	 */
	public Long count() {
		return conArtRepo.count();
	}
}