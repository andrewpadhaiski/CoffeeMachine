package by.epam.coffeemashine.model.services.interfaces;

import java.util.List;

import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.services.exception.ServiceException;

public interface ICoffeeService {

	List<Coffee> getCoffees() throws ServiceException;

	boolean checkPresence(Integer coffeeId) throws ServiceException;

	Coffee getCurrentCoffee();

	void fill(Integer coffeeId, Integer serves) throws ServiceException;
}