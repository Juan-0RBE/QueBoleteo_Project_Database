package co.edu.unbosque.queboleteo.repository;

import co.edu.unbosque.queboleteo.entity.Lugar;
import co.edu.unbosque.queboleteo.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LugarRepository extends JpaRepository<Lugar, Long> {

	/**
	 * 
	 * Encuentra un lugar por asiento y zona.
	 * 
	 * @param numeroAsiento
	 * @param zona
	 * @return Lugar encontrado o null
	 */
	Optional<Lugar> findByNumeroAsientoAndZona(Integer numeroAsiento, Zona zona);

	/**
	 * Retorna todos los lugares de una zona
	 * 
	 * @param idZona
	 * @return Lista de lugares encontrados o null
	 */
	List<Lugar> findByZona_IdZona(Long idZona);

	/**
	 * Lugares libres para una ZonaConcierto específica Son los que no tienen ningún
	 * boleto activo para ese IdPrecio.
	 * 
	 * @param idZona
	 * @param idPrecio
	 * @return
	 */
	@Query("SELECT l FROM Lugar l WHERE l.zona.idZona = :idZona " + "AND l.idLugar NOT IN ("
			+ "  SELECT b.lugar.idLugar FROM Boleto b " + "  WHERE b.zonaConcierto.idPrecio = :idPrecio "
			+ "  AND b.estadoBoleto = 'ACTIVO')")
	List<Lugar> findLugaresLibresPorZonaConcierto(@Param("idZona") Long idZona, @Param("idPrecio") Long idPrecio);

	/**
	 * Verifica que un lugar específico esté libre para una ZonaConcierto y
	 * pertenezca a la zona correcta
	 * 
	 * @param idLugar
	 * @param idZona
	 * @param idPrecio
	 * @return
	 */
	@Query("SELECT l FROM Lugar l WHERE l.idLugar = :idLugar " + "AND l.zona.idZona = :idZona "
			+ "AND l.idLugar NOT IN (" + "  SELECT b.lugar.idLugar FROM Boleto b "
			+ "  WHERE b.zonaConcierto.idPrecio = :idPrecio " + "  AND b.estadoBoleto = 'ACTIVO')")
	Optional<Lugar> findLugarLibreEspecifico(@Param("idLugar") Long idLugar, @Param("idZona") Long idZona,
			@Param("idPrecio") Long idPrecio);

	/**
	 * Cuenta los lugares totales de una zona
	 * 
	 * @param idZona
	 * @return
	 */
	Integer countByZona_IdZona(Long idZona);

	/**
	 * Cuenta los lugares totales de una zona
	 * 
	 * @param zona
	 * @return
	 */
	long countByZona(Zona zona);
}