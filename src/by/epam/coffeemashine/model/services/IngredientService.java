package by.epam.coffeemashine.model.services;

import java.util.List;
import java.util.Set;

import by.epam.coffeemashine.model.data.dao.IngredientDAO;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.dao.interfaces.IIngredientDAO;
import by.epam.coffeemashine.model.data.entities.Ingredient;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IIngredientService;

public class IngredientService implements IIngredientService {
	private static final IIngredientDAO INGREDIENTS = IngredientDAO.getInstance();
	private static final IIngredientService INSTANCE = new IngredientService();
	
	private IngredientService() {
	}
	
	public static IIngredientService getInstance(){
		return INSTANCE;
	} 
	
	@Override
	public List<Ingredient> getIngredients() throws ServiceException {
		List<Ingredient> availableIngredients = null;
		try {
			availableIngredients = INGREDIENTS.findAll();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return availableIngredients;
	}

	@Override
	public boolean checkPresence(Set<Ingredient> ingredients) throws ServiceException {
		boolean flag = true;
		for (Ingredient ingredient: ingredients) {			
			Ingredient dbIngredient = null;
			try {
				dbIngredient = INGREDIENTS.findEntityById(ingredient.getId());
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
			if (dbIngredient.getQuantity() < ingredient.getQuantity()) {
				flag = false;
			}
		}
		return flag;		
	}

	@Override
	public void fill(Integer ingredientId, Integer serves) throws ServiceException {
		Ingredient ingredient = null;
		try {
			ingredient = INGREDIENTS.findEntityById(ingredientId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		int currentServes = ingredient.getQuantity();
		currentServes += serves;
		ingredient.setQuantity(currentServes);
		try {
			INGREDIENTS.update(ingredient);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
