package com.bank.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bank.model.Admin;

public class AdminDAO {

	public Admin getAdminByUsername(String username) {
		Admin admin = null;
		try (Connection conn = DatabaseUtility.getConnection()) {
			String query = "SELECT * FROM admins WHERE username = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String password = rs.getString("password");
				admin = new Admin(username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}

	public boolean validateAdmin(String username, String password) {
		Admin admin = getAdminByUsername(username);
		if (admin != null) {
			return admin.getPassword().equals(password); // In real-world, hash and compare
		}
		return false;
	}
}
