package by.epam.coffeemashine.model.services.interfaces;

import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.services.exception.ServiceException;

public interface ISecurityService {
	boolean login(String enterLogin, String enterPass) throws ServiceException;

	boolean register(String enterName, String enterLogin, String enterPass)
			throws ServiceException;
	
	User getCurrentUser();
}
