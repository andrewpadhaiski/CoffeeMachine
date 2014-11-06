package by.epam.coffeemashine.model.services.interfaces;

import java.math.BigDecimal;

import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.services.exception.ServiceException;

public interface IAccountService {

	boolean deposit(User user, BigDecimal amount) throws ServiceException;

	boolean withdraw(User user, BigDecimal amount) throws ServiceException;

}
