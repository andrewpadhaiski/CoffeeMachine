package by.epam.coffeemashine.model.services;

import java.util.List;

import by.epam.coffeemashine.model.data.dao.CoffeeDAO;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.dao.interfaces.ICoffeeDAO;
import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.ICoffeeService;

public class CoffeeService implements ICoffeeService {
	private static final ICoffeeDAO COFFEES = CoffeeDAO.getInstance();
	private static final ICoffeeService INSTANCE = new CoffeeService();
	private Coffee coffee;
	
	private CoffeeService() {
	}

	public static ICoffeeService getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Coffee> getCoffees() throws ServiceException {
		List<Coffee> coffees = null;
		try {
			coffees = COFFEES.findAll();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return coffees;
	}

	@Override
	public boolean checkPresence(Integer coffeeId) throws ServiceException {
		boolean flag = false;
		Coffee dbCoffee = null;
		try {
			dbCoffee = COFFEES.findEntityById(coffeeId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		if (dbCoffee.getQuantity() > 0) {
			flag = true;
			this.coffee = dbCoffee;
		}
		return flag;
	}

	@Override
	public Coffee getCurrentCoffee() {
		return coffee;
	}

	@Override
	public void fill(Integer coffeeId, Integer serves) throws ServiceException {
		Coffee coffee = null;
		try {
			coffee = COFFEES.findEntityById(coffeeId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		int currentServes = coffee.getQuantity();
		currentServes += serves;
		coffee.setQuantity(currentServes);
		try {
			COFFEES.update(coffee);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
