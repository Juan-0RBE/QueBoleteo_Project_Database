package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.ConOrg;
import co.edu.unbosque.queboleteo.entity.ConOrgId;
import co.edu.unbosque.queboleteo.entity.Organizador;

public interface ConOrgRepository extends JpaRepository<ConOrg, ConOrgId> {

	/**
	 * Busca todas las asociaciones de un concierto.
	 *
	 * @param concierto Objeto Concierto
	 * @return Lista de ConOrg para ese concierto
	 */
	List<ConOrg> findByConcierto(Concierto concierto);

	/**
	 * Busca todas las asociaciones de un organizador.
	 *
	 * @param organizador Objeto Organizador
	 * @return Lista de ConOrg para ese organizador
	 */
	List<ConOrg> findByOrganizador(Organizador organizador);
}