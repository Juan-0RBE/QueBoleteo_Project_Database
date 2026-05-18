package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unbosque.queboleteo.entity.Boleto;
import co.edu.unbosque.queboleteo.entity.Venta;
import co.edu.unbosque.queboleteo.entity.ZonaConcierto;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {

	/**
	 * Busca todos los boletos de una venta.
	 *
	 * @param venta Objeto Venta
	 * @return Lista de boletos de esa venta
	 */
	List<Boleto> findByVenta(Venta venta);

	/**
	 * Busca todos los boletos de una zona-concierto.
	 *
	 * @param zonaConcierto Objeto ZonaConcierto
	 * @return Lista de boletos de esa zona-concierto
	 */
	List<Boleto> findByZonaConcierto(ZonaConcierto zonaConcierto);

	
	/**
	 * Hace una consulta para ver si el usuario tiene comprado un boleto de un concierto
	 * 
	 * @param correo
	 * @param idConcierto
	 * @return
	 */
	@Query("SELECT COUNT(b) > 0 FROM Boleto b " + "WHERE b.venta.usuario.correo = :correo "
			+ "AND b.zonaConcierto.concierto.idConcierto = :idConcierto")
	boolean existsByUsuarioAndConcierto(@Param("correo") String correo, @Param("idConcierto") Long idConcierto);

}