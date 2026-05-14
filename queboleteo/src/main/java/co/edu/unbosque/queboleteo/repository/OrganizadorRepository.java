package co.edu.unbosque.queboleteo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.queboleteo.entity.Organizador;

@Repository
public interface OrganizadorRepository
		extends JpaRepository<Organizador, String> {

	/**
	 * Busca un organizador por correo.
	 * 
	 * @param correoOrganizador correo del organizador
	 * @return Organizador encontrado o null
	 */
	public Organizador findByCorreoOrganizador(
			String correoOrganizador);

}