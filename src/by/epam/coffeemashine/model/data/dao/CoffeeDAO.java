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
import by.epam.coffeemashine.model.data.dao.interfaces.ICoffeeDAO;
import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.data.entities.enums.CoffeeSortEnum;
import by.epam.coffeemashine.model.data.entities.enums.CoffeeTypeEnum;

public class CoffeeDAO extends AbstractDAO implements ICoffeeDAO {

	private static final String SQL_SELECT_ALL_COFFEES = "SELECT * FROM coffees";
	private static final String SQL_SELECT_COFFEE_BY_ID = "SELECT * FROM coffees WHERE coffee_id=?";
	private static final String SQL_DELETE_COFFEE_BY_ID = "DELETE FROM coffees WHERE coffee_id=?";
	private static final String SQL_INSERT_COFFEE = "INSERT INTO coffees(sort, type, price, quantity) VALUES(?,?,?,?)";
	private static final String SQL_UPDATE_COFFEE = "UPDATE coffees SET sort=?, type=?, price=?, quantity=? WHERE coffee_id=?";

	private static final ICoffeeDAO INSTANCE = new CoffeeDAO();

	private CoffeeDAO() {
	}

	public static ICoffeeDAO getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Coffee> findAll() throws DAOException {
		List<Coffee> coffees = new ArrayList<>();
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Coffee coffee = null;
		cn = getConnection();
		try {
			st = cn.createStatement();
			rs = st.executeQuery(SQL_SELECT_ALL_COFFEES);
			while (rs.next()) {
				coffee = readCoffee(rs);
				coffees.add(coffee);
			}
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return coffees;
	}

	@Override
	public Coffee findEntityById(Integer id) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Coffee coffee = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_SELECT_COFFEE_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (!rs.next()) {
				return null;
			}
			coffee = readCoffee(rs);
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return coffee;
	}

	@Override
	public boolean delete(Integer id) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_DELETE_COFFEE_BY_ID);
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
	public boolean create(Coffee entity) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_INSERT_COFFEE);
			st.setString(1, entity.getSort().toString());
			st.setString(2, entity.getType().toString());
			st.setBigDecimal(3, entity.getPrice());
			st.setInt(4, entity.getQuantity());
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
	public Coffee update(Coffee entity) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_UPDATE_COFFEE);
			st.setString(1, entity.getSort().toString());
			st.setString(2, entity.getType().toString());
			st.setBigDecimal(3, entity.getPrice());
			st.setInt(4, entity.getQuantity());
			st.setInt(5, entity.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_UPDATE_SQL, e);
		} finally {
			closeStatement(st);
			returnConnection(cn);
		}
		return entity;
	}

	private Coffee readCoffee(ResultSet rs) throws SQLException {
		Coffee coffee = new Coffee();
		coffee.setId(rs.getInt(DbAttribute.COFFEE_ID));

		String strType = rs.getString(DbAttribute.COFFEE_TYPE);
		CoffeeTypeEnum type = CoffeeTypeEnum.valueOf(strType);
		coffee.setType(type);

		String strSort = rs.getString(DbAttribute.COFFEE_SORT);
		CoffeeSortEnum sort = CoffeeSortEnum.valueOf(strSort);
		coffee.setSort(sort);

		coffee.setPrice(rs.getBigDecimal(DbAttribute.COFFEE_PRICE));
		coffee.setQuantity(rs.getInt(DbAttribute.COFFEE_QUANTITY));
		return coffee;
	}

}
