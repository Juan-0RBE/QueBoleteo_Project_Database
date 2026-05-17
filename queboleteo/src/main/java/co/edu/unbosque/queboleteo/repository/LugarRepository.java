package co.edu.unbosque.queboleteo.repository;

import co.edu.unbosque.queboleteo.entity.Lugar;
import co.edu.unbosque.queboleteo.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LugarRepository extends JpaRepository<Lugar, Long> {

	// Verifica duplicado al crear un lugar manualmente
	Optional<Lugar> findByNumeroAsientoAndZona(Integer numeroAsiento, Zona zona);

	// Todos los lugares de una zona (para contar capacidad total)
	List<Lugar> findByZona_IdZona(Long idZona);

	// Lugares libres para una ZonaConcierto específica:
	// son los que NO tienen ningún boleto activo para ese IdPrecio
	@Query("SELECT l FROM Lugar l WHERE l.zona.idZona = :idZona " + "AND l.idLugar NOT IN ("
			+ "  SELECT b.lugar.idLugar FROM Boleto b " + "  WHERE b.zonaConcierto.idPrecio = :idPrecio "
			+ "  AND b.estadoBoleto = 'ACTIVO')")
	List<Lugar> findLugaresLibresPorZonaConcierto(@Param("idZona") Long idZona, @Param("idPrecio") Long idPrecio);

	// Verifica que un lugar específico esté libre para una ZonaConcierto
	// y pertenezca a la zona correcta
	@Query("SELECT l FROM Lugar l WHERE l.idLugar = :idLugar " + "AND l.zona.idZona = :idZona "
			+ "AND l.idLugar NOT IN (" + "  SELECT b.lugar.idLugar FROM Boleto b "
			+ "  WHERE b.zonaConcierto.idPrecio = :idPrecio " + "  AND b.estadoBoleto = 'ACTIVO')")
	Optional<Lugar> findLugarLibreEspecifico(@Param("idLugar") Long idLugar, @Param("idZona") Long idZona,
			@Param("idPrecio") Long idPrecio);

	// Cuenta los lugares totales de una zona
	// (para inicializar CantidadDisponible en ZonaConciertoService)
	Integer countByZona_IdZona(Long idZona);
	
	long countByZona(Zona zona);
}

/**
 * Busca todos los lugares de una zona.
 *
 * @param zona Objeto Zona
 * @return Lista de lugares de esa zona
 * 
 *         List<Lugar> findByZona(Zona zona);
 * 
 *         Busca un lugar por su número de asiento y zona. Útil para verificar
 *         duplicados en create.
 *
 * @param numeroAsiento Número del asiento
 * @param zona          Objeto Zona
 * @return Optional con el lugar si existe
 * 
 *         Optional<Lugar> findByNumeroAsientoAndZona(Integer numeroAsiento,
 *         Zona zona);
 * 
 *         // Para zona general — lugares libres de una zona List<Lugar>
 *         findByZona_IdZonaAndBoletoIsNull(Long idZona);
 * 
 *         // Para zona con asientos — verificar que un lugar específico // esté
 *         libre Y pertenezca a la zona correcta Optional<Lugar>
 *         findByIdLugarAndZona_IdZonaAndBoletoIsNull(Long idLugar, Long
 *         idZona);
 * 
 * 
 *         // Contar lugares libres de una zona (para inicializar
 *         CantidadDisponible) Integer countByZona_IdZonaAndBoletoIsNull(Long
 *         idZona);
 */