package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.ArtistaIndividual;
import co.edu.unbosque.queboleteo.entity.GruArt;
import co.edu.unbosque.queboleteo.entity.GruArtId;
import co.edu.unbosque.queboleteo.entity.Grupo;

public interface GruArtRepository extends JpaRepository<GruArt, GruArtId>{
	
	/**
	 * Busca todas las asociaciones de un artista.
	 * 
	 * @param grupo objeto Grupo.
	 * @return Lista de GruArt para ese grupo.
	 */
	List<GruArt> findByGrupo(Grupo grupo);
	
	/**
	 * Busca todas las asociaciones de un grupo.
	 * 
	 * @param artista objeto Artista.
	 * @return Lista de GruArt para ese grupo.
	 */
	List<GruArt> findByArtista(ArtistaIndividual artista);
	
	/**
	 * Busca todas las asociaciones de un rol.
	 * 
	 * @param rol string de un rol
	 * @return Lista de GruArt para ese grupo
	 */
	List<GruArt> findByRol(String rol);

}
