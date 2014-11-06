package by.epam.coffeemashine.model.data.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epam.coffeemashine.model.data.connection.ConnectionPoolException;
import by.epam.coffeemashine.model.data.connection.ConnectionPool;
import by.epam.coffeemashine.model.data.connection.IConnectionPool;
import by.epam.coffeemashine.model.data.constant.DbMessage;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;

public abstract class AbstractDAO {
	public static final IConnectionPool POOL = ConnectionPool.getInstance();

	public Connection getConnection() throws DAOException {
		Connection cn;
		try {
			cn = POOL.getConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}
		return cn;
	}

	public void returnConnection(Connection cn) throws DAOException {
		try {
			POOL.returnConnection(cn);
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}
	}

	public void closeStatement(Statement st) throws DAOException {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_CLOSE_STATEMENT, e);
		}
	}

	public void closeResultSet(ResultSet rs) throws DAOException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_CLOSE_RESULTSET, e);
		}
	}

	public void openTransaction(Connection cn) throws DAOException {
		try {
			cn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_OPEN_TRANSACTION, e);
		}
	}
	
	public void closeTransaction(Connection cn) throws DAOException {
		try {
			cn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DAOException(DbMessage.ERROR_CLOSE_TRANSACTION, e);
		}
	}
}
