package com.tyoku.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	String file;

	public DBManager(String file) {
		this.file = file;
	}

	public Connection createConnection() throws ClassNotFoundException {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:" + file);
			return con;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}