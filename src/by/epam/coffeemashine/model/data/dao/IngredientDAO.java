package by.epam.coffeemashine.model.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epam.coffeemashine.model.data.constant.DbAttribute;
import by.epam.coffeemashine.model.data.constant.DbMessage;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.dao.interfaces.IIngredientDAO;
import by.epam.coffeemashine.model.data.entities.Ingredient;
import by.epam.coffeemashine.model.data.entities.enums.IngredientTypeEnum;

public class IngredientDAO extends AbstractDAO implements IIngredientDAO {

	private static final String SQL_SELECT_ALL_INGREDIENTS = "SELECT * FROM ingredients";
	private static final String SQL_SELECT_INGREDIENT_BY_ID = "SELECT * FROM ingredients WHERE ingredient_id=?";
	private static final String SQL_DELETE_INGREDIENT_BY_ID = "DELETE FROM ingredients WHERE ingredient_id=?";
	private static final String SQL_INSERT_INGREDIENT = "INSERT INTO ingredients(type, price, quantity) VALUES(?,?,?)";
	private static final String SQL_UPDATE_INGREDIENT = "UPDATE ingredients SET type=?, price=?, quantity=? WHERE ingredient_id=?";

	private static final IIngredientDAO INSTANCE = new IngredientDAO();

	private IngredientDAO() {
	}

	public static final IIngredientDAO getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Ingredient> findAll() throws DAOException {
		List<Ingredient> ingredients = new ArrayList<>();
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Ingredient ingredient = null;
		cn = getConnection();
		try {
			st = cn.createStatement();
			rs = st.executeQuery(SQL_SELECT_ALL_INGREDIENTS);
			while (rs.next()) {
				ingredient = readIngredient(rs);
				ingredients.add(ingredient);
			}
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return ingredients;
	}

	@Override
	public Ingredient findEntityById(Integer id) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Ingredient ingredient = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_SELECT_INGREDIENT_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (!rs.next()) {
				return null;
			}
			ingredient = readIngredient(rs);
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return ingredient;
	}

	@Override
	public boolean delete(Integer id) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_DELETE_INGREDIENT_BY_ID);
			st.setInt(1, id);
			st.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_DELETE_SQL, e);
		} finally {
			closeStatement(st);
			returnConnection(cn);
		}
		return flag;
	}

	@Override
	public boolean create(Ingredient entity) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_INSERT_INGREDIENT);
			st.setString(1, entity.getType().toString());
			st.setBigDecimal(2, entity.getPrice());
			st.setInt(3, entity.getQuantity());
			st.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_INSERT_SQL, e);
		} finally {
			closeStatement(st);
			returnConnection(cn);
		}
		return flag;
	}

	@Override
	public Ingredient update(Ingredient entity) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_UPDATE_INGREDIENT);
			st.setString(1, entity.getType().toString());
			st.setBigDecimal(2, entity.getPrice());
			st.setInt(3, entity.getQuantity());
			st.setInt(4, entity.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_UPDATE_SQL, e);
		} finally {
			closeStatement(st);
			returnConnection(cn);
		}
		return entity;
	}

	private Ingredient readIngredient(ResultSet rs) throws SQLException {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(rs.getInt(DbAttribute.INGREDIENT_ID));

		String strType = rs.getString(DbAttribute.INGREDIENT_TYPE);
		IngredientTypeEnum type = IngredientTypeEnum.valueOf(strType);
		ingredient.setType(type);

		ingredient.setPrice(rs.getBigDecimal(DbAttribute.INGREDIENT_PRICE));
		ingredient.setQuantity(rs.getInt(DbAttribute.INGREDIENT_QUANTITY));
		return ingredient;
	}

}
