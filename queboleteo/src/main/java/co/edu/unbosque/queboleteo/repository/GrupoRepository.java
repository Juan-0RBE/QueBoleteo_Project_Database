package co.edu.unbosque.queboleteo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.queboleteo.entity.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	/**
	 * Busca un grupo por su nombre.
	 * 
	 * @param nombreGrupo nombre del grupo
	 * @return Grupo encontrado o null
	 */
	public Grupo findByNombreGrupo(String nombreGrupo);

}