package by.epam.coffeemashine.model.data.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epam.coffeemashine.model.data.constant.DbAttribute;
import by.epam.coffeemashine.model.data.constant.DbMessage;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.dao.interfaces.IUserDAO;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;

public class UserDAO extends AbstractDAO implements IUserDAO {

	private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
	private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";
	private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
	private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id=?";
	private static final String SQL_INSERT_USER = "INSERT INTO users(name, login, password, role, balance) VALUES(?,?,?,?,?)";
	private static final String SQL_UPDATE_USER = "UPDATE users SET name=?, login=?, password=?, role=?, balance=? WHERE user_id=?";

	private static final IUserDAO INSTANCE = new UserDAO();

	private UserDAO() {
	}

	public static IUserDAO getInstance() {
		return INSTANCE;
	}

	@Override
	public List<User> findAll() throws DAOException {
		List<User> users = new ArrayList<>();
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		User user = null;
		cn = getConnection();
		try {
			st = cn.createStatement();
			rs = st.executeQuery(SQL_SELECT_ALL_USERS);
			while (rs.next()) {
				user = readUser(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return users;
	}

	@Override
	public User findEntityById(Integer id) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		User user = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_SELECT_USER_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (!rs.next()) {
				return null;
			}
			user = readUser(rs);
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return user;
	}

	@Override
	public boolean delete(Integer id) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_DELETE_USER_BY_ID);
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
	public boolean create(User entity) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_INSERT_USER);
			st.setString(1, entity.getName());
			st.setString(2, entity.getLogin());
			st.setString(3, entity.getPassword());
			st.setString(4, entity.getRole().toString());
			st.setBigDecimal(5, entity.getBalance());
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
	public User update(User entity) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_UPDATE_USER);
			st.setString(1, entity.getName());
			st.setString(2, entity.getLogin());
			st.setString(3, entity.getPassword());
			st.setString(4, entity.getRole().toString());
			st.setBigDecimal(5, entity.getBalance());
			st.setInt(6, entity.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_UPDATE_SQL, e);
		} finally {
			closeStatement(st);
			returnConnection(cn);
		}
		return entity;
	}

	@Override
	public User findEntityByLogin(String login) throws DAOException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		User user = null;
		cn = getConnection();
		try {
			st = cn.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
			st.setString(1, login);
			rs = st.executeQuery();
			if (!rs.next()) {
				return null;
			}
			user = readUser(rs);
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_SELECT_SQL, e);
		} finally {
			closeResultSet(rs);
			closeStatement(st);
			returnConnection(cn);
		}
		return user;
	}

	private User readUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(DbAttribute.USER_ID));
		user.setName(rs.getString(DbAttribute.USER_NAME));
		user.setLogin(rs.getString(DbAttribute.USER_LOGIN));
		user.setPassword(rs.getString(DbAttribute.USER_PASSWORD));

		String strRole = rs.getString(DbAttribute.USER_ROLE);
		UserRoleEnum role = UserRoleEnum.valueOf(strRole);
		user.setRole(role);

		user.setBalance(rs.getBigDecimal(DbAttribute.USER_BALANCE));
		return user;
	}
}