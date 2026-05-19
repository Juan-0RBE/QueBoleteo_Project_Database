package co.edu.unbosque.queboleteo.service;

import java.util.List;

public interface CRUDOperation<D> {

	/**
	 * Opreacion CRUD basica para crear un dto
	 * 
	 * @param newData
	 * @return
	 */
	public int create(D newData);
	
	/**
	 * Opreacion CRUD para obtener todos los dto
	 * 
	 * @return
	 */
	public List<D> getAll();
	/**
	 * Opreacion CRUD para eliminar dto
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	/**
	 * Opreacion CRUD para actualizar un dto
	 * 
	 * @param id
	 * @param newData
	 * @return
	 */
	public int updateById(Long id, D newData);
	/**
	 * Opreacion CRUD contar cuantos registros de una entidad hay
	 * 
	 * @return
	 */
	public Long count();
	/**
	 * Opreacion CRUD para verificar que un dto exista
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public boolean exist(Long id);
	/**
	 * Opreacion CRUD para obtener un dto por id
	 * 
	 * @param id
	 * @return
	 */
	public D getById(Long id);


}
