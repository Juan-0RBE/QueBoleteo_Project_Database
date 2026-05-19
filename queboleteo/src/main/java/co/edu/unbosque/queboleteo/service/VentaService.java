package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.VentaDTO;
import co.edu.unbosque.queboleteo.entity.Usuario;
import co.edu.unbosque.queboleteo.entity.Venta;
import co.edu.unbosque.queboleteo.repository.UsuarioRepository;
import co.edu.unbosque.queboleteo.repository.VentaRepository;

import co.edu.unbosque.queboleteo.dto.CompraRequestDto;
import co.edu.unbosque.queboleteo.dto.CompraResponseDto;
import co.edu.unbosque.queboleteo.entity.Boleto;
import co.edu.unbosque.queboleteo.entity.Lugar;
import co.edu.unbosque.queboleteo.entity.ZonaConcierto;
import co.edu.unbosque.queboleteo.repository.BoletoRepository;
import co.edu.unbosque.queboleteo.repository.LugarRepository;
import co.edu.unbosque.queboleteo.repository.ZonaConciertoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class VentaService implements CRUDOperation<VentaDTO> {

	@Autowired
	private VentaRepository ventaRepo;

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private ZonaConciertoRepository zonaConciertoRepo;

	@Autowired
	private BoletoRepository boletoRepo;

	@Autowired
	private LugarRepository lugarRepo;

	@Autowired
	private EmailService emailService;

	public VentaService() {
	}

	/**
	 * Convierte un DTO a entidad.
	 *
	 * @param dto DTO de la venta
	 * @return Entidad Venta lista para persistir
	 */
	private Venta toEntity(VentaDTO dto) {
		Venta entity = new Venta();
		entity.setValorTotal(dto.getValorTotal());
		entity.setFechaVenta(dto.getFechaVenta());

		if (dto.getCorreoUsuario() != null) {
			Optional<Usuario> usuario = usuarioRepo.findById(dto.getCorreoUsuario());
			if (usuario.isPresent()) {
			    entity.setUsuario(usuario.get());
			}

		}

		return entity;
	}

	/**
	 * Convierte una entidad a DTO. Extrae solo el correo del usuario, no el objeto
	 * completo.
	 *
	 * @param entity Entidad Venta
	 * @return DTO de la venta
	 */
	private VentaDTO toDTO(Venta entity) {
		VentaDTO dto = new VentaDTO();
		dto.setIdVenta(entity.getIdVenta());
		dto.setValorTotal(entity.getValorTotal());
		dto.setFechaVenta(entity.getFechaVenta());
		if (entity.getUsuario() != null) {
			dto.setCorreoUsuario(entity.getUsuario().getCorreo());
		}
		return dto;
	}

	/**
	 * Crea una nueva venta en la base de datos. un campo de negocio único — cada
	 * venta es inherentemente nueva.
	 *
	 * @param newData DTO con los datos de la venta
	 * @return 0 si fue exitoso
	 */
	@Override
	public int create(VentaDTO newData) {
		ventaRepo.save(toEntity(newData));
		return 0;
	}

	/**
	 * Obtiene todas las ventas registradas.
	 *
	 * @return Lista de DTOs de ventas
	 */
	@Override
	public List<VentaDTO> getAll() {
		List<Venta> entityList = ventaRepo.findAll();
		List<VentaDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene una venta por su ID.
	 *
	 * @param id ID de la venta
	 * @return DTO de la venta si existe, null en caso contrario
	 */
	@Override
	public VentaDTO getById(Long id) {
		Optional<Venta> found = ventaRepo.findById(id);
		if (found.isPresent()) {
			return toDTO(found.get());
		}
		return null;
	}

	/**
	 * Elimina una venta por su ID.
	 *
	 * @param id ID de la venta
	 * @return 0 si fue eliminada correctamente, 1 si no existe
	 */
	@Override
	public int deleteById(Long id) {
		Optional<Venta> found = ventaRepo.findById(id);
		if (found.isPresent()) {
			ventaRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza una venta existente.
	 *
	 * @param id      ID de la venta a actualizar
	 * @param newData Nuevos datos de la venta
	 * @return 0 si actualizó correctamente, 1 si no existe
	 */
	@Override
	public int updateById(Long id, VentaDTO newData) {
		Optional<Venta> found = ventaRepo.findById(id);
		if (found.isPresent()) {
			Venta entity = found.get();
			entity.setValorTotal(newData.getValorTotal());
			entity.setFechaVenta(newData.getFechaVenta());

			if (newData.getCorreoUsuario() != null) {
				Optional<Usuario> usuario = usuarioRepo.findById(newData.getCorreoUsuario());
				if (usuario.isPresent()) {
				    entity.setUsuario(usuario.get());
				}
			}

			ventaRepo.save(entity);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de ventas registradas.
	 *
	 * @return Cantidad total de ventas
	 */
	@Override
	public Long count() {
		return ventaRepo.count();
	}

	/**
	 * Verifica si existe una venta con el ID dado.
	 *
	 * @param id ID de la venta
	 * @return true si existe, false en caso contrario
	 */
	@Override
	public boolean exist(Long id) {
		return ventaRepo.existsById(id);
	}

	/**
	 * Realiza la compra de boletos para una zona-concierto específica. Crea la
	 * venta, los boletos y asigna lugares disponibles en una sola transacción.
	 *
	 * @param dto DTO con correo del usuario, id de zona-concierto y cantidad
	 * @return DTO con resumen de la compra realizada
	 */
	@Transactional
	public CompraResponseDto realizarCompraSinCorreo(CompraRequestDto dto) {

		// Verificar usuario
		Usuario usuario = usuarioRepo.findById(dto.getCorreoUsuario())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getCorreoUsuario()));

		// Verificar ZonaConcierto
		ZonaConcierto zc = zonaConciertoRepo.findById(dto.getIdZonaConcierto()).orElseThrow(
				() -> new RuntimeException("ZonaConcierto no encontrada con id: " + dto.getIdZonaConcierto()));

		// Verificar que el concierto no haya pasado
		LocalDateTime ahora = LocalDateTime.now();
		if (zc.getConcierto().getFechaConcierto().isBefore(ahora)) {
			throw new RuntimeException("No se pueden comprar boletos para un concierto que ya ocurrió.");
		}

		// Verificar disponibilidad en el contador
		if (zc.getCantidadDisponible() < dto.getCantidad()) {
			throw new RuntimeException("No hay suficientes lugares. Disponibles: " + zc.getCantidadDisponible()
					+ ", solicitados: " + dto.getCantidad());
		}

		// Obtener los lugares a asignar según el tipo de zona
		List<Lugar> lugaresAAsignar = new ArrayList<>();

		if (zc.getZona().getTieneAsiento()) {

			// zona con asientos
			if (dto.getIdsLugaresElegidos() == null || dto.getIdsLugaresElegidos().isEmpty()) {
				throw new RuntimeException("Esta zona tiene asientos. Debes enviar idsLugaresElegidos.");
			}
			if (dto.getIdsLugaresElegidos().size() != dto.getCantidad()) {
				throw new RuntimeException(
						"La cantidad de lugares elegidos debe coincidir con la cantidad solicitada.");
			}

			// Verificar disponibilidad por ZonaConcierto
			for (Long idLugar : dto.getIdsLugaresElegidos()) {
				Lugar lugar = lugarRepo.findLugarLibreEspecifico(idLugar, zc.getZona().getIdZona(), zc.getIdPrecio())
						.orElseThrow(() -> new RuntimeException("El lugar con id " + idLugar
								+ " no existe, no pertenece a esta zona, o ya está ocupado para este concierto."));
				lugaresAAsignar.add(lugar);
			}

		} else {

			// zona general
			// Buscar libres por ZonaConcierto específica
			lugaresAAsignar = lugarRepo.findLugaresLibresPorZonaConcierto(zc.getZona().getIdZona(), zc.getIdPrecio());

			if (lugaresAAsignar.size() < dto.getCantidad()) {
				throw new RuntimeException("No hay suficientes lugares físicos libres. Libres: "
						+ lugaresAAsignar.size() + ", solicitados: " + dto.getCantidad());
			}
		}

		// Calcular valor total
		BigDecimal valorTotal = zc.getPrecio().multiply(BigDecimal.valueOf(dto.getCantidad()));

		// Crear y guardar la Venta
		Venta venta = new Venta();
		venta.setUsuario(usuario);
		venta.setFechaVenta(LocalDateTime.now());
		venta.setValorTotal(valorTotal);
		venta = ventaRepo.save(venta);

		// Crear Boletos y asignar Lugares
		List<Long> codigosBoletos = new ArrayList<>();
		List<Long> idsLugares = new ArrayList<>();

		for (int i = 0; i < dto.getCantidad(); i++) {

			Lugar lugar = lugaresAAsignar.get(i);

			Boleto boleto = new Boleto();
			boleto.setEstadoBoleto("ACTIVO");
			boleto.setZonaConcierto(zc);
			boleto.setVenta(venta);
			boleto.setLugar(lugar);
			boleto = boletoRepo.save(boleto);

			codigosBoletos.add(boleto.getCodigoBoleto());
			idsLugares.add(lugar.getIdLugar());
		}

		// Decrementar disponibilidad
		zc.setCantidadDisponible(zc.getCantidadDisponible() - dto.getCantidad());
		zonaConciertoRepo.save(zc);

		// Retornar resumen
		CompraResponseDto response = new CompraResponseDto();
		response.setIdVenta(venta.getIdVenta());
		response.setValorTotal(valorTotal);
		response.setCodigosBoletos(codigosBoletos);
		response.setIdsLugares(idsLugares);

		return response;
	}

	@Transactional
	public CompraResponseDto realizarCompra(CompraRequestDto dto) {

		// Verificar usuario
		Usuario usuario = usuarioRepo.findById(dto.getCorreoUsuario())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getCorreoUsuario()));

		// Verificar ZonaConcierto
		ZonaConcierto zc = zonaConciertoRepo.findById(dto.getIdZonaConcierto()).orElseThrow(
				() -> new RuntimeException("ZonaConcierto no encontrada con id: " + dto.getIdZonaConcierto()));

		// Verificar fecha
		LocalDateTime ahora = LocalDateTime.now();
		if (zc.getConcierto().getFechaConcierto().isBefore(ahora)) {
			throw new RuntimeException("No se pueden comprar boletos para un concierto que ya ocurrió.");
		}

		// Verificar disponibilidad
		if (zc.getCantidadDisponible() < dto.getCantidad()) {
			throw new RuntimeException("No hay suficientes lugares. Disponibles: " + zc.getCantidadDisponible()
					+ ", solicitados: " + dto.getCantidad());
		}

		// Lugares
		List<Lugar> lugaresAAsignar = new ArrayList<>();

		if (zc.getZona().getTieneAsiento()) {

			if (dto.getIdsLugaresElegidos() == null || dto.getIdsLugaresElegidos().isEmpty()) {
				throw new RuntimeException("Esta zona tiene asientos. Debes enviar idsLugaresElegidos.");
			}

			if (dto.getIdsLugaresElegidos().size() != dto.getCantidad()) {
				throw new RuntimeException("La cantidad de lugares elegidos debe coincidir.");
			}

			for (Long idLugar : dto.getIdsLugaresElegidos()) {

				Lugar lugar = lugarRepo.findLugarLibreEspecifico(idLugar, zc.getZona().getIdZona(), zc.getIdPrecio())
						.orElseThrow(() -> new RuntimeException("El lugar " + idLugar + " no está disponible"));

				lugaresAAsignar.add(lugar);
			}

		} else {

			lugaresAAsignar = lugarRepo.findLugaresLibresPorZonaConcierto(zc.getZona().getIdZona(), zc.getIdPrecio());

			if (lugaresAAsignar.size() < dto.getCantidad()) {
				throw new RuntimeException("No hay suficientes lugares libres.");
			}
		}

		// Total
		BigDecimal valorTotal = zc.getPrecio().multiply(BigDecimal.valueOf(dto.getCantidad()));

		// Venta
		Venta venta = new Venta();
		venta.setUsuario(usuario);
		venta.setFechaVenta(LocalDateTime.now());
		venta.setValorTotal(valorTotal);
		venta = ventaRepo.save(venta);

		// Boletos
		List<Long> codigosBoletos = new ArrayList<>();
		List<Long> idsLugares = new ArrayList<>();
		List<String> detallesBoletos = new ArrayList<>();

		for (int i = 0; i < dto.getCantidad(); i++) {

			Lugar lugar = lugaresAAsignar.get(i);

			Boleto boleto = new Boleto();
			boleto.setEstadoBoleto("ACTIVO");
			boleto.setZonaConcierto(zc);
			boleto.setVenta(venta);
			boleto.setLugar(lugar);

			boleto = boletoRepo.save(boleto);

			codigosBoletos.add(boleto.getCodigoBoleto());
			idsLugares.add(lugar.getIdLugar());

			String detalleLugar;

			if (lugar.getFila() != null && lugar.getNumeroAsiento() != null) {

				detalleLugar = "Zona: " + zc.getZona().getNombreZona() + ", fila: " + lugar.getFila() + ", asiento: "
						+ lugar.getNumeroAsiento();

			} else {

				detalleLugar = "Zona: " + zc.getZona().getNombreZona() + " | General (Sin asiento asignado)";
			}

			detallesBoletos.add(detalleLugar);
		}

		// actualizar disponibilidad
		zc.setCantidadDisponible(zc.getCantidadDisponible() - dto.getCantidad());
		zonaConciertoRepo.save(zc);

		// Response
		CompraResponseDto response = new CompraResponseDto();
		response.setIdVenta(venta.getIdVenta());
		response.setValorTotal(valorTotal);
		response.setCodigosBoletos(codigosBoletos);
		response.setIdsLugares(idsLugares);

		try {

			emailService.enviarCorreoCompra(usuario.getCorreo(), usuario.getNombreUsuario(),
					zc.getConcierto().getNombreConcierto(), dto.getCantidad(), valorTotal, detallesBoletos);

			System.out.println("Correo enviado correctamente");

		} catch (Exception e) {

			System.out.println("Error enviando correo: " + e.getMessage());
		}

		return response;
	}

	public ZonaConcierto getZonaConcierto(Long id) {
		return zonaConciertoRepo.findById(id).orElseThrow(() -> new RuntimeException("ZonaConcierto no encontrada"));
	}

	public List<Lugar> getLugaresLibres(Long idZona, Long idPrecio) {
		return lugarRepo.findLugaresLibresPorZonaConcierto(idZona, idPrecio);
	}

}