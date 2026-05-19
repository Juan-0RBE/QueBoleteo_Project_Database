package co.edu.unbosque.queboleteo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.queboleteo.entity.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, String> {

	/**
	 * Busca una sede por el nombre de la sede.
	 * 
	 * @param nombreSede
	 * @return Sede encontrada o null
	 */
	Sede findByNombreSede(String nombreSede);

}