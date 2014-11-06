package by.epam.coffeemashine.model.data.dao.interfaces;

import java.util.List;

import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.entities.AbstractEntity;

/** 
 * Interface {@code IDAO} is a base interface
 * consisting essential functionality for operations with a database.
 *  
 * @author Andrei Padhaiski
 * 
*/
public interface IDAO<K, T extends AbstractEntity> {
	
	List<T> findAll() throws DAOException;

	T findEntityById(K id) throws DAOException;

	boolean delete(K id) throws DAOException;

	boolean create(T entity) throws DAOException;

	T update(T entity) throws DAOException;
}
