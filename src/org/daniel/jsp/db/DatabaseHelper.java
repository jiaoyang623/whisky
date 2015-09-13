package org.daniel.jsp.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseHelper {
	private static DatabaseHelper INSTANCE = new DatabaseHelper();
	private DataSource mDataSource;

	public static DatabaseHelper getInstance() {
		return INSTANCE;
	}

	private DatabaseHelper() {
		try {
			Context cxt = new InitialContext();
			mDataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/Whisky");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Connection openConnection() throws SQLException {
		return mDataSource.getConnection();
	}
}
