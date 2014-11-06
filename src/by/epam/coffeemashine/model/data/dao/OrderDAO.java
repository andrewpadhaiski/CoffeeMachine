package by.epam.coffeemashine.model.data.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.epam.coffeemashine.model.data.constant.DbAttribute;
import by.epam.coffeemashine.model.data.constant.DbMessage;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.dao.interfaces.ICoffeeDAO;
import by.epam.coffeemashine.model.data.dao.interfaces.IOrderDAO;
import by.epam.coffeemashine.model.data.dao.interfaces.IUserDAO;
import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.data.entities.Ingredient;
import by.epam.coffeemashine.model.data.entities.Order;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.data.entities.enums.IngredientTypeEnum;

public class OrderDAO extends AbstractDAO implements IOrderDAO {

	private static final String SQL_SELECT_QUANTITY_BY_COFFEE_ID = "SELECT quantity FROM coffees WHERE coffee_id=?";
	private static final String SQL_UPDATE_QUANTITY_BY_COFFEE_ID = "UPDATE coffees SET quantity=? WHERE coffee_id=?";

	private static final String SQL_SELECT_QUANTITY_BY_INGREDIENT_ID = "SELECT quantity FROM ingredients WHERE ingredient_id=?";
	private static final String SQL_UPDATE_QUANTITY_BY_INGREDIENT_ID = "UPDATE ingredients SET quantity=? WHERE ingredient_id=?";

	private static final String SQL_SELECT_BALANCE_BY_USER_ID = "SELECT balance FROM users WHERE user_id=?";
	private static final String SQL_UPDATE_BALANCE_BY_USER_ID = "UPDATE users SET balance=? WHERE user_id=?";

	private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders";
	private static final String SQL_SELECT_INGREDIENTS_BY_ORDER_ID = "SELECT additions.order_id, ingredients.ingredient_id, ingredients.type, ingredients.price, additions.quantity FROM additions INNER JOIN ingredients ON ingredients.ingredient_id = additions.ingredient_id WHERE order_id=?";
	private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id=?";
	private static final String SQL_SELECT_ORDER_BY_USER_ID = "SELECT * FROM orders WHERE user_id=?";
	private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id=?";
	private static final String SQL_DELETE_ADDITIONS_BY_ORDER_ID = "DELETE FROM additions WHERE order_id=?";
	private static final String SQL_INSERT_ORDER = "INSERT INTO orders(amount, date, coffee_id, user_id) VALUES(?,?,?,?)";
	private static final String SQL_INSERT_ADDITION = "INSERT INTO additions(order_id, ingredient_id, quantity) VALUES(?,?,?)";

	private static final IUserDAO USERS = UserDAO.getInstance();
	private static final ICoffeeDAO COFFEES = CoffeeDAO.getInstance();

	private static final IOrderDAO INSTANCE = new OrderDAO();

	private OrderDAO() {
	}

	public static IOrderDAO getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Order> findAll() throws DAOException {
		List<Order> orders = new ArrayList<>();
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Order order = null;
		cn = getConnection();
		try {
			st = cn.createStatement();
			rs = st.executeQuery(SQL_SELECT_ALL_ORDERS);
			while (rs.next()) {
				order = readOrder(rs, cn);
				orders.add(order);
			}
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return orders;
	}

	@Override
	public Order findEntityById(Integer id) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Order order = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_SELECT_ORDER_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (!rs.next()) {
				return null;
			}
			order = readOrder(rs, cn);
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return order;
	}

	@Override
	public List<Order> findOrdersByUserId(Integer id) throws DAOException {
		List<Order> orders = new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Order order = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_SELECT_ORDER_BY_USER_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			while (rs.next()) {
				order = readOrder(rs, cn);
				orders.add(order);
			}
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return orders;
	}

	@Override
	public boolean delete(Integer id) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		PreparedStatement sti = null;
		boolean flag = false;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_DELETE_ORDER_BY_ID);
			st.setInt(1, id);
			st.executeUpdate();
			sti = cn.prepareStatement(SQL_DELETE_ADDITIONS_BY_ORDER_ID);
			sti.setInt(1, id);
			sti.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_DELETE_SQL, e);
		} finally {
			closeStatement(sti);
			closeStatement(st);
			returnConnection(cn);
		}
		return flag;
	}

	@Override
	public boolean create(Order entity) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_INSERT_ORDER,
					Statement.RETURN_GENERATED_KEYS);
			st.setBigDecimal(1, entity.getAmount());
			st.setDate(2, Date.valueOf(entity.getDate()));
			st.setDouble(3, entity.getCoffee().getId());
			st.setInt(4, entity.getUser().getId());
			int affectedRows = st.executeUpdate();
			if (affectedRows == 0) {
				return flag;
			}
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				int orderId = generatedKeys.getInt(1);
				PreparedStatement sti = null;
				for (Ingredient ingredient : entity.getIngredients()) {
					try {
						sti = cn.prepareStatement(SQL_INSERT_ADDITION);
						sti.setInt(1, orderId);
						sti.setInt(2, ingredient.getId());
						sti.setInt(3, ingredient.getQuantity());
						sti.executeUpdate();
					} catch (SQLException e) {
						throw new DAOException(DbMessage.ERROR_INSERT_SQL, e);
					} finally {
						closeStatement(sti);
					}
				}
			}
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
	public Order update(Order entity) throws DAOException {
		throw new DAOException();
	}
	
	@Override
	public boolean executeTransaction(Order order) throws DAOException {
		boolean flag = false;

		Connection coffeeConnection = getConnection();
		Connection ingredientConnection = getConnection();
		Connection userConnection = getConnection();
		Connection adminConnection = getConnection();

		openTransaction(coffeeConnection);
		openTransaction(ingredientConnection);
		openTransaction(userConnection);
		openTransaction(adminConnection);

		PreparedStatement checkCoffeeStatement = null;
		PreparedStatement updateCoffeeStatement = null;

		PreparedStatement checkIngredientStatement = null;
		PreparedStatement updateIngredientStatement = null;

		PreparedStatement checkBalanceStatement = null;
		PreparedStatement updateUserBalanceStatement = null;
		PreparedStatement adminBalanceStatement = null;
		PreparedStatement updateAdminBalanceStatement = null;

		ResultSet coffeeSet = null;
		ResultSet ingredientSet = null;
		ResultSet userSet = null;
		ResultSet adminSet = null;

		try {
			checkCoffeeStatement = coffeeConnection
					.prepareStatement(SQL_SELECT_QUANTITY_BY_COFFEE_ID);
			checkCoffeeStatement.setInt(1, order.getCoffee().getId());
			coffeeSet = checkCoffeeStatement.executeQuery();

			int coffeeQuantity = 0;
			while (coffeeSet.next()) {
				coffeeQuantity = coffeeSet.getInt(1);
			}
			if (coffeeQuantity == 0) {
				return flag;
			}

			updateCoffeeStatement = coffeeConnection
					.prepareStatement(SQL_UPDATE_QUANTITY_BY_COFFEE_ID);
			updateCoffeeStatement.setInt(1, --coffeeQuantity);
			updateCoffeeStatement.setInt(2, order.getCoffee().getId());
			updateCoffeeStatement.executeUpdate();

			for (Ingredient ingredient : order.getIngredients()) {
				try {
					checkIngredientStatement = ingredientConnection
							.prepareStatement(SQL_SELECT_QUANTITY_BY_INGREDIENT_ID);
					checkIngredientStatement.setInt(1, ingredient.getId());
					ingredientSet = checkIngredientStatement.executeQuery();

					int ingredientQuantity = 0;
					while (ingredientSet.next()) {
						ingredientQuantity = ingredientSet.getInt(1);
					}
					if (ingredientQuantity < ingredient.getQuantity()) {
						return flag;
					}
					int newQuantity = ingredientQuantity
							- ingredient.getQuantity();
					updateIngredientStatement = ingredientConnection
							.prepareStatement(SQL_UPDATE_QUANTITY_BY_INGREDIENT_ID);
					updateIngredientStatement.setInt(1, newQuantity);
					updateIngredientStatement.setInt(2, ingredient.getId());
					updateIngredientStatement.executeUpdate();
				} finally {
					closeResultSet(ingredientSet);
					closeStatement(checkIngredientStatement);
					closeStatement(updateIngredientStatement);
				}
			}

			checkBalanceStatement = userConnection
					.prepareStatement(SQL_SELECT_BALANCE_BY_USER_ID);
			checkBalanceStatement.setInt(1, order.getUser().getId());
			userSet = checkBalanceStatement.executeQuery();

			BigDecimal userBalance = null;
			while (userSet.next()) {
				userBalance = userSet.getBigDecimal(1);
			}
			if (userBalance.compareTo(order.getAmount()) < 0) {
				return flag;
			}

			BigDecimal newBalance = userBalance.subtract(order.getAmount());
			updateUserBalanceStatement = userConnection
					.prepareStatement(SQL_UPDATE_BALANCE_BY_USER_ID);
			updateUserBalanceStatement.setBigDecimal(1, newBalance);
			updateUserBalanceStatement.setInt(2, order.getUser().getId());
			updateUserBalanceStatement.executeUpdate();

			adminBalanceStatement = adminConnection
					.prepareStatement(SQL_SELECT_BALANCE_BY_USER_ID);
			adminBalanceStatement.setInt(1, 1);
			adminSet = adminBalanceStatement.executeQuery();

			BigDecimal adminBalance = null;
			while (adminSet.next()) {
				adminBalance = adminSet.getBigDecimal(1);
			}

			BigDecimal newAdminBalance = adminBalance.add(order.getAmount());
			updateAdminBalanceStatement = adminConnection
					.prepareStatement(SQL_UPDATE_BALANCE_BY_USER_ID);
			updateAdminBalanceStatement.setBigDecimal(1, newAdminBalance);
			updateAdminBalanceStatement.setInt(2, 1);
			updateAdminBalanceStatement.executeUpdate();		

			coffeeConnection.commit();
			ingredientConnection.commit();
			userConnection.commit();
			adminConnection.commit();
			
			create(order);
			
			flag = true;
		} catch (SQLException e) {
			try {
				coffeeConnection.rollback();
				ingredientConnection.rollback();
				userConnection.rollback();
				adminConnection.rollback();
			} catch (SQLException ex) {
				throw new DAOException(DbMessage.ERROR_TRANSACTION_SQL, e);
			}
			throw new DAOException(DbMessage.ERROR_TRANSACTION_SQL, e);
		} finally {
			closeResultSet(coffeeSet);
			closeStatement(checkCoffeeStatement);
			closeStatement(updateCoffeeStatement);

			closeResultSet(userSet);
			closeStatement(checkBalanceStatement);
			closeStatement(updateUserBalanceStatement);

			closeResultSet(adminSet);
			closeStatement(adminBalanceStatement);
			closeStatement(updateAdminBalanceStatement);

			closeTransaction(coffeeConnection);
			closeTransaction(ingredientConnection);
			closeTransaction(userConnection);
			closeTransaction(adminConnection);

			returnConnection(coffeeConnection);
			returnConnection(ingredientConnection);
			returnConnection(userConnection);
			returnConnection(adminConnection);
		}
		return flag;

	}

	private Order readOrder(ResultSet rs, Connection cn) throws SQLException,
			DAOException {
		Order order = new Order();
		order.setId(rs.getInt(DbAttribute.ORDER_ID));
		order.setAmount(rs.getBigDecimal(DbAttribute.ORDER_AMOUNT));
		order.setDate(rs.getDate(DbAttribute.ORDER_DATE).toLocalDate());

		int userId = rs.getInt(DbAttribute.USER_ID);
		User user = USERS.findEntityById(userId);
		order.setUser(user);

		int coffeeId = rs.getInt(DbAttribute.COFFEE_ID);
		Coffee coffee = COFFEES.findEntityById(coffeeId);
		order.setCoffee(coffee);

		Set<Ingredient> ingredients = new HashSet<>();
		PreparedStatement sti = null;
		ResultSet rsi = null;
		Ingredient ingredient = null;
		try {
			sti = cn.prepareStatement(SQL_SELECT_INGREDIENTS_BY_ORDER_ID);
			sti.setInt(1, order.getId());
			rsi = sti.executeQuery();
			while (rsi.next()) {
				ingredient = readIngredient(rsi);
				ingredients.add(ingredient);
			}
		} finally {
			closeResultSet(rsi);
			closeStatement(sti);
		}
		order.setIngredients(ingredients);
		return order;
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
