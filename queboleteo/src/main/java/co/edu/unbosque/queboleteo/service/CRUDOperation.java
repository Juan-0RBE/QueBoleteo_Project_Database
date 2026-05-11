package co.edu.unbosque.queboleteo.service;

import java.util.List;

public interface CRUDOperation<D> {

	public int create(D newData);
	public List<D> getAll();
	public int deleteById(Long id);
	public int updateById(Long id, D newData);
	public Long count();
	public boolean exist(Long id);
	public D getById(Long id);


}
