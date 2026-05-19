package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.BoletoDTO;
import co.edu.unbosque.queboleteo.entity.Boleto;
import co.edu.unbosque.queboleteo.entity.Venta;
import co.edu.unbosque.queboleteo.entity.ZonaConcierto;
import co.edu.unbosque.queboleteo.repository.BoletoRepository;
import co.edu.unbosque.queboleteo.repository.VentaRepository;
import co.edu.unbosque.queboleteo.repository.ZonaConciertoRepository;

@Service
public class BoletoService implements CRUDOperation<BoletoDTO> {

	@Autowired
	private BoletoRepository boletoRepo;

	@Autowired
	private ZonaConciertoRepository zonaConciertoRepo;

	@Autowired
	private VentaRepository ventaRepo;

	public BoletoService() {
	}

	/**
	 * Convierte un DTO a entidad.
	 *
	 * @param dto DTO del boleto
	 * @return Entidad Boleto lista para persistir
	 */
	private Boleto toEntity(BoletoDTO dto) {
		Boleto entity = new Boleto();
		entity.setEstadoBoleto(dto.getEstadoBoleto());

		if (dto.getIdPrecio() != null) {
			Optional<ZonaConcierto> zonaConcierto = zonaConciertoRepo.findById(dto.getIdPrecio());
			zonaConcierto.ifPresent(entity::setZonaConcierto);
		}

		if (dto.getIdVenta() != null) {
			Optional<Venta> venta = ventaRepo.findById(dto.getIdVenta());
			venta.ifPresent(entity::setVenta);
		}

		return entity;
	}

	/**
	 * Convierte una entidad a DTO. 
	 *
	 * @param entity Entidad Boleto
	 * @return DTO del boleto
	 */
	private BoletoDTO toDTO(Boleto entity) {
		BoletoDTO dto = new BoletoDTO();
		dto.setCodigoBoleto(entity.getCodigoBoleto());
		dto.setEstadoBoleto(entity.getEstadoBoleto());

		if (entity.getZonaConcierto() != null) {
			dto.setIdPrecio(entity.getZonaConcierto().getIdPrecio());
		}

		if (entity.getVenta() != null) {
			dto.setIdVenta(entity.getVenta().getIdVenta());
		}

		return dto;
	}

	/**
	 * Crea un nuevo boleto.
	 *
	 * @param newData DTO con los datos del boleto
	 * @return 0 si fue exitoso
	 */
	@Override
	public int create(BoletoDTO newData) {
		boletoRepo.save(toEntity(newData));
		return 0;
	}

	/**
	 * Obtiene todos los boletos registrados.
	 *
	 * @return Lista de DTOs de boletos
	 */
	@Override
	public List<BoletoDTO> getAll() {
		List<Boleto> entityList = boletoRepo.findAll();
		List<BoletoDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene un boleto por su código.
	 *
	 * @param id Código del boleto
	 * @return DTO del boleto si existe, null en caso contrario
	 */
	@Override
	public BoletoDTO getById(Long id) {
		Optional<Boleto> found = boletoRepo.findById(id);
		if (found.isPresent()) {
			return toDTO(found.get());
		}
		return null;
	}

	/**
	 * Elimina un boleto por su código.
	 *
	 * @param id Código del boleto
	 * @return 0 si fue eliminado correctamente, 1 si no existe
	 */
	@Override
	public int deleteById(Long id) {
		Optional<Boleto> found = boletoRepo.findById(id);
		if (found.isPresent()) {
			boletoRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un boleto existente.
	 *
	 * @param id      Código del boleto a actualizar
	 * @param newData Nuevos datos del boleto
	 * @return 0 si actualizó correctamente, 1 si no existe
	 */
	@Override
	public int updateById(Long id, BoletoDTO newData) {
		Optional<Boleto> found = boletoRepo.findById(id);
		if (found.isPresent()) {
			Boleto entity = found.get();
			entity.setEstadoBoleto(newData.getEstadoBoleto());

			if (newData.getIdPrecio() != null) {
				Optional<ZonaConcierto> zonaConcierto = zonaConciertoRepo.findById(newData.getIdPrecio());
				zonaConcierto.ifPresent(entity::setZonaConcierto);
			}

			if (newData.getIdVenta() != null) {
				Optional<Venta> venta = ventaRepo.findById(newData.getIdVenta());
				venta.ifPresent(entity::setVenta);
			}

			boletoRepo.save(entity);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de boletos registrados.
	 *
	 * @return Cantidad total de boletos
	 */
	@Override
	public Long count() {
		return boletoRepo.count();
	}

	/**
	 * Verifica si existe un boleto con el código dado.
	 *
	 * @param id Código del boleto
	 * @return true si existe, false en caso contrario
	 */
	@Override
	public boolean exist(Long id) {
		return boletoRepo.existsById(id);
	}
}