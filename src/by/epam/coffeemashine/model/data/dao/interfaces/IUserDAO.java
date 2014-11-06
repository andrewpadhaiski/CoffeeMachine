package by.epam.coffeemashine.model.data.dao.interfaces;

import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.entities.User;

public interface IUserDAO extends IDAO<Integer, User> {
	
	User findEntityByLogin(String login) throws DAOException;
}
