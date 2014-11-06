package by.epam.coffeemashine.model.services.interfaces;

import java.util.List;
import java.util.Set;

import by.epam.coffeemashine.model.data.entities.Ingredient;
import by.epam.coffeemashine.model.services.exception.ServiceException;

public interface IIngredientService {

	List<Ingredient> getIngredients() throws ServiceException;

	boolean checkPresence(Set<Ingredient> ingredients) throws ServiceException;

	void fill(Integer ingredientId, Integer serves) throws ServiceException;

}