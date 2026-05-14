package co.edu.unbosque.queboleteo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Boleto;
import co.edu.unbosque.queboleteo.entity.Lugar;
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
     * Busca el boleto asociado a un lugar específico.
     * por eso retorna Optional y no List.
     *
     * @param lugar Objeto Lugar
     * @return Optional con el boleto si existe
     */
    Optional<Boleto> findByLugar(Lugar lugar);
}