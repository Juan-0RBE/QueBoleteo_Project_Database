package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.ConGru;
import co.edu.unbosque.queboleteo.entity.ConGruId;
import co.edu.unbosque.queboleteo.entity.Grupo;

public interface ConGruRepository extends JpaRepository<ConGru, ConGruId> {

	/**
	 * Busca todas las asociaciones de un concierto.
	 *
	 * @param concierto Objeto Concierto
	 * @return Lista de ConGru para ese concierto
	 */
	List<ConGru> findByConcierto(Concierto concierto);

	/**
	 * Busca todas las asociaciones de un grupo.
	 *
	 * @param grupo Objeto Grupo
	 * @return Lista de ConGru para ese grupo
	 */
	List<ConGru> findByGrupo(Grupo grupo);
}