package by.epam.coffeemashine.model.services;

import java.math.BigDecimal;

import by.epam.coffeemashine.model.data.dao.UserDAO;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.dao.interfaces.IUserDAO;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.ISecurityService;

public class SecurityService implements ISecurityService {

	private static final IUserDAO USERS = UserDAO.getInstance();
	private static final ISecurityService INSTANCE = new SecurityService();
	private User user;

	private SecurityService() {
	}

	public static ISecurityService getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean login(String enterLogin, String enterPass)
			throws ServiceException {
		boolean flag = false;
		User user = null;
		try {
			user = USERS.findEntityByLogin(enterLogin);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		if (user != null && user.getPassword().equals(enterPass)) {
			this.user = user;
			flag = true;
		} 
		return flag;
	}

	@Override
	public boolean register(String enterName, String enterLogin, String enterPass)
			throws ServiceException {
		boolean flag = false;
		User user = null;
		try {
			user = USERS.findEntityByLogin(enterLogin);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		if (user != null) {
			return flag;
		}
		user = new User();
		user.setName(enterName);
		user.setLogin(enterLogin);
		user.setPassword(enterPass);
		user.setRole(UserRoleEnum.USER);
		user.setBalance(BigDecimal.ZERO);
		try {
			flag = USERS.create(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		try {
			this.user = USERS.findEntityByLogin(enterLogin);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return flag;
	}
	
	@Override
	public User getCurrentUser() {
		return user;
	}
}
