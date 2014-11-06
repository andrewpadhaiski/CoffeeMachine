package by.epam.coffeemashine.model.services;

import java.math.BigDecimal;

import by.epam.coffeemashine.model.data.dao.UserDAO;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.dao.interfaces.IUserDAO;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IAccountService;

public class AccountService implements IAccountService {

	private static final BigDecimal DEPOSIT_LIMIT = new BigDecimal(100);
	
	private static final IUserDAO USERS = UserDAO.getInstance();

	private static final IAccountService INSTANCE = new AccountService();

	private AccountService() {
	}

	public static IAccountService getInstance() {
		return INSTANCE;
	}
	
	@Override
	public boolean deposit(User user, BigDecimal amount) throws ServiceException {
		boolean flag = false;
		if(amount.compareTo(DEPOSIT_LIMIT) > 0) {
			return flag;
		}
		BigDecimal balance = user.getBalance();
		BigDecimal newBalance = balance.add(amount);
		user.setBalance(newBalance);
		try {
			USERS.update(user);
			flag = true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return flag;
	}

	@Override
	public boolean withdraw(User user, BigDecimal amount) throws ServiceException {
		boolean flag = false;
		BigDecimal actualBalance = user.getBalance();
		if (actualBalance.compareTo(amount) < 0) {
			return flag;
		}
		BigDecimal newBalance = actualBalance.subtract(amount);
		user.setBalance(newBalance);
		try {
			USERS.update(user);
			flag = true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return flag;
	}
}
