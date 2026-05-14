package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Genero;
import co.edu.unbosque.queboleteo.entity.GruGen;
import co.edu.unbosque.queboleteo.entity.GruGenId;
import co.edu.unbosque.queboleteo.entity.Grupo;

public interface GruGenRepository extends JpaRepository<GruGen, GruGenId> {

	/**
	 * Busca todas las asociaciones de un grupo.
	 *
	 * @param grupo Objeto Grupo
	 * @return Lista de GruGen para ese grupo
	 */
	List<GruGen> findByGrupo(Grupo grupo);

	/**
	 * Busca todas las asociaciones de un género.
	 *
	 * @param genero Objeto Genero
	 * @return Lista de GruGen para ese género
	 */
	List<GruGen> findByGenero(Genero genero);
}