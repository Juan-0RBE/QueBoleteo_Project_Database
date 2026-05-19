package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ConOrgDTO;
import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.ConOrg;
import co.edu.unbosque.queboleteo.entity.ConOrgId;
import co.edu.unbosque.queboleteo.entity.Organizador;
import co.edu.unbosque.queboleteo.repository.ConciertoRepository;
import co.edu.unbosque.queboleteo.repository.ConOrgRepository;
import co.edu.unbosque.queboleteo.repository.OrganizadorRepository;

@Service
public class ConOrgService {

	@Autowired
	private ConOrgRepository conOrgRepo;

	@Autowired
	private ConciertoRepository conciertoRepo;

	@Autowired
	private OrganizadorRepository organizadorRepo;

	public ConOrgService() {
	}

	/**
	 * Convierte una entidad a DTO.
	 *
	 * @param entity Entidad ConOrg
	 * @return DTO de la asociación
	 */
	private ConOrgDTO toDTO(ConOrg entity) {
		ConOrgDTO dto = new ConOrgDTO();
		dto.setIdConcierto(entity.getConcierto().getIdConcierto());
		dto.setNombreOrganizador(entity.getOrganizador().getNombreOrganizador());
		return dto;
	}

	/**
	 * Crea una nueva asociación concierto-organizador.
	 *
	 * @param dto DTO con idConcierto y nombreOrganizador
	 * @return 0 si fue exitoso, 1 si concierto o organizador no existen, 2 si la
	 *         asociación ya existe
	 */
	public int create(ConOrgDTO dto) {
		Optional<Concierto> concierto = conciertoRepo.findById(dto.getIdConcierto());
		Optional<Organizador> organizador = organizadorRepo.findById(dto.getNombreOrganizador());

		if (concierto.isEmpty() || organizador.isEmpty())
			return 1;

		ConOrgId id = new ConOrgId(dto.getIdConcierto(), dto.getNombreOrganizador());
		if (conOrgRepo.existsById(id))
			return 2;

		conOrgRepo.save(new ConOrg(concierto.get(), organizador.get()));
		return 0;
	}

	/**
	 * Obtiene todas las asociaciones concierto-organizador.
	 *
	 * @return Lista de DTOs
	 */
	public List<ConOrgDTO> getAll() {
		List<ConOrg> entityList = conOrgRepo.findAll();
		List<ConOrgDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los organizadores de un concierto específico.
	 *
	 * @param idConcierto ID del concierto
	 * @return Lista de DTOs filtrada por concierto
	 */
	public List<ConOrgDTO> getByConciertoId(Long idConcierto) {
		Optional<Concierto> concierto = conciertoRepo.findById(idConcierto);
		if (concierto.isEmpty())
			return new ArrayList<>();
		List<ConOrg> entityList = conOrgRepo.findByConcierto(concierto.get());
		List<ConOrgDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene todos los conciertos de un organizador específico.
	 *
	 * @param nombreOrganizador Nombre del organizador (PK)
	 * @return Lista de DTOs filtrada por organizador
	 */
	public List<ConOrgDTO> getByOrganizadorNombre(String nombreOrganizador) {
		Optional<Organizador> organizador = organizadorRepo.findById(nombreOrganizador);
		if (organizador.isEmpty())
			return new ArrayList<>();
		List<ConOrg> entityList = conOrgRepo.findByOrganizador(organizador.get());
		List<ConOrgDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Elimina una asociación concierto-organizador por sus claves.
	 *
	 * @param idConcierto       ID del concierto
	 * @param nombreOrganizador Nombre del organizador
	 * @return 0 si fue eliminada correctamente, 1 si no existe
	 */
	public int deleteByIds(Long idConcierto, String nombreOrganizador) {
		ConOrgId id = new ConOrgId(idConcierto, nombreOrganizador);
		if (conOrgRepo.existsById(id)) {
			conOrgRepo.deleteById(id);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de asociaciones concierto-organizador.
	 *
	 * @return Cantidad total de asociaciones
	 */
	public Long count() {
		return conOrgRepo.count();
	}
}