package by.epam.coffeemashine.model.data.connection;

import java.sql.Connection;

/** 
 * Interface {@code IConnectionPool} need to provide loosed coupling 
 * architecture between DAO layer and connection pool layer.
 * DAO layer only knows this interface about connection pool layer.
 * 
 * @author Andrei Padhaiski
 * 
*/
public interface IConnectionPool {
	
	void initPool() throws ConnectionPoolException;

	Connection getConnection() throws ConnectionPoolException;

	void returnConnection(Connection cn) throws ConnectionPoolException;

	void closePool() throws ConnectionPoolException;
}