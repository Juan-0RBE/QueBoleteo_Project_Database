package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ConGruDTO;
import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.ConGru;
import co.edu.unbosque.queboleteo.entity.ConGruId;
import co.edu.unbosque.queboleteo.entity.Grupo;
import co.edu.unbosque.queboleteo.repository.ConciertoRepository;
import co.edu.unbosque.queboleteo.repository.ConGruRepository;
import co.edu.unbosque.queboleteo.repository.GrupoRepository;

@Service
public class ConGruService {

	@Autowired
	private ConGruRepository conGruRepo;

	@Autowired
	private ConciertoRepository conciertoRepo;

	@Autowired
	private GrupoRepository grupoRepo;

	public ConGruService() {
	}

	/**
	 * Convierte una entidad a DTO.
	 *
	 * @param entity Entidad ConGru
	 * @return DTO de la asociación
	 */
	private ConGruDTO toDTO(ConGru entity) {
		ConGruDTO dto = new ConGruDTO();
		dto.setIdConcierto(entity.getConcierto().getIdConcierto());
		dto.setIdGrupo(entity.getGrupo().getIdGrupo());
		return dto;
	}

	/**
	 * Crea una nueva asociación concierto-grupo.
	 *
	 * @param dto DTO con idConcierto e idGrupo
	 * @return 0 si fue exitoso, 1 si concierto o grupo no existen, 2 si la
	 *         asociación ya existe
	 */
	public int create(ConGruDTO dto) {
		Optional<Concierto> concierto = conciertoRepo.findById(dto.getIdConcierto());
		Optional<Grupo> grupo = grupoRepo.findById(dto.getIdGrupo());

		if (concierto.isEmpty() || grupo.isEmpty())
			return 1;

		ConGruId id = new ConGruId(dto.getIdConcierto(), dto.getIdGrupo());
		if (conGruRepo.existsById(id))
			return 2;

		conGruRepo.save(new ConGru(concierto.get(), grupo.get()));
		return 0;
	}

	/**
	 * Obtiene todas las asociaciones concierto-grupo.
	 *
	 * @return Lista de DTOs
	 */
	public List<ConGruDTO> getAll() {
		List<ConGru> entityList = conGruRepo.findAll();
		List<ConGruDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los grupos de un concierto específico.
	 *
	 * @param idConcierto ID del concierto
	 * @return Lista de DTOs filtrada por concierto
	 */
	public List<ConGruDTO> getByConciertoId(Long idConcierto) {
		Optional<Concierto> concierto = conciertoRepo.findById(idConcierto);
		if (concierto.isEmpty())
			return new ArrayList<>();
		List<ConGru> entityList = conGruRepo.findByConcierto(concierto.get());
		List<ConGruDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los conciertos de un grupo específico.
	 *
	 * @param idGrupo ID del grupo
	 * @return Lista de DTOs filtrada por grupo
	 */
	public List<ConGruDTO> getByGrupoId(Long idGrupo) {
		Optional<Grupo> grupo = grupoRepo.findById(idGrupo);
		if (grupo.isEmpty())
			return new ArrayList<>();
		List<ConGru> entityList = conGruRepo.findByGrupo(grupo.get());
		List<ConGruDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Elimina una asociación concierto-grupo por sus IDs.
	 *
	 * @param idConcierto ID del concierto
	 * @param idGrupo     ID del grupo
	 * @return 0 si fue eliminada correctamente, 1 si no existe
	 */
	public int deleteByIds(Long idConcierto, Long idGrupo) {
		ConGruId id = new ConGruId(idConcierto, idGrupo);
		if (conGruRepo.existsById(id)) {
			conGruRepo.deleteById(id);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de asociaciones concierto-grupo.
	 *
	 * @return Cantidad total de asociaciones
	 */
	public Long count() {
		return conGruRepo.count();
	}
}