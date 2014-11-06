package by.epam.coffeemashine.model.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import by.epam.coffeemashine.model.data.constant.DbMessage;
import by.epam.coffeemashine.model.data.constant.DbParameter;

import com.mysql.jdbc.Driver;

/** 
 * Class {@code ConnectionPool} provide implementation of 
 * {@code IConnectionPool} interface based on {@code BlockingQueue}.
 * Because users of the system share database connections, 
 * we made connection pool singular object, applying singleton pattern.
 * 
 * @author Andrei Padhaiski
 * 
*/
public class ConnectionPool implements IConnectionPool {

	private static final Properties INFO = new Properties();

	private static final BlockingQueue<Connection> POOL = new LinkedBlockingQueue<>(
			DbParameter.POOL_SIZE);

	private static final IConnectionPool INSTANCE = new ConnectionPool();;

	private ConnectionPool() {
	}

	public static IConnectionPool getInstance() {
		return INSTANCE;
	}

	@Override
	public void initPool() throws ConnectionPoolException {
		INFO.put(DbParameter.USER, DbParameter.USER_VALUE);
		INFO.put(DbParameter.PASSWORD, DbParameter.PASSWORD_VALUE);
		INFO.put(DbParameter.RECONNECT, DbParameter.RECONNECT_VALUE);
		INFO.put(DbParameter.ENCODING, DbParameter.ENCODING_VALUE);
		INFO.put(DbParameter.UNICODE, DbParameter.UNICODE_VALUE);
		try {
			DriverManager.registerDriver(new Driver());
			Connection cn = null;
			try {
				for (int i = 0; i < DbParameter.POOL_SIZE; i++) {
					cn = DriverManager.getConnection(DbParameter.URL, INFO);
					POOL.offer(cn);
				}
			} catch (SQLException e) {
				throw new ConnectionPoolException(
						DbMessage.ERROR_CREATE_CONNECTION, e);
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException(DbMessage.ERROR_DRIVER, e);
		}
	}

	@Override
	public Connection getConnection() throws ConnectionPoolException {
		Connection cn = null;
		try {
			cn = POOL.take();
		} catch (InterruptedException e) {
			throw new ConnectionPoolException(DbMessage.ERROR_GET_CONNECTION, e);
		}
		return cn;
	}

	@Override
	public void returnConnection(Connection cn) throws ConnectionPoolException {
		if (cn != null) {
			try {
				if (!cn.isClosed()) {
					POOL.offer(cn);
					return;
				}
			} catch (SQLException e) {
				throw new ConnectionPoolException(
						DbMessage.ERROR_RETURN_CONNECTION, e);
			}
		}
		try {
			cn = DriverManager.getConnection(DbParameter.URL, INFO);
		} catch (SQLException e) {
			throw new ConnectionPoolException(DbMessage.ERROR_GET_CONNECTION, e);
		}
		POOL.offer(cn);
	}

	@Override
	public void closePool() throws ConnectionPoolException {
		for (Connection cn : POOL) {
			try {
				cn.close();
			} catch (SQLException e) {
				throw new ConnectionPoolException(
						DbMessage.ERROR_CLOSE_CONNECTION, e);
			}
			POOL.clear();
		}
	}
}
