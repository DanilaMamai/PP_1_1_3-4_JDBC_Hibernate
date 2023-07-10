package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `user` (`id` INT NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NULL, `lastname` VARCHAR(45) NULL, `age` INT(3) NULL, PRIMARY KEY (`id`), UNIQUE INDEX `idnew_table_UNIQUE` (`id` ASC) VISIBLE);";
	private static final String CLEAR_TABLE = "TRUNCATE TABLE `user`";
	private static final String DELETE = "DELETE FROM `user` WHERE id=?;";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS `user`";
	private static final String INSERT = "INSERT INTO `user` (name, lastname, age) VALUES (?, ?, ?);";
	
	public UserDaoJDBCImpl() {

	}

	public void createUsersTable() {
		try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
			statement.execute(CREATE_TABLE);
		} catch (Exception e) {
			System.err.println("ERROR CREATE TABLE");
		}
	}

	public void dropUsersTable() {
		try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
			statement.execute(DROP_TABLE);
		} catch (Exception e) {
			System.err.println("ERROR DROP TABLE");
		}
	}

	public void saveUser(String name, String lastName, byte age) {
		try (Connection connection = Util.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, lastName);
			preparedStatement.setByte(3, age);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.err.println("ERROR SAVE USER");
		}
	}

	public void removeUserById(long id) {
		try (Connection connection = Util.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
			preparedStatement.setLong(1, id);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.err.println("ERROR DELETE USER");
		}
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();

		try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `user`");
			while (resultSet.next()) {
				String name = resultSet.getString(2);
				String lastname = resultSet.getString(3);
				byte age = resultSet.getByte(4);
				
				User user = new User(name, lastname, age) {
					@Override
					public String toString() {
						// TODO Auto-generated method stub
						return String.format("name: %s, lastname: %s, age: %d", name, lastname, age);
					}
				};

				users.add(user);
			}
		} catch (Exception e) {
			System.err.println("ERROR GET USERS");
		}

		return users;
	}

	public void cleanUsersTable() {
		try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
			statement.execute(CLEAR_TABLE);
		} catch (Exception e) {
			System.err.println("ERROR CLEAR TABLE");
		}
	}
}
