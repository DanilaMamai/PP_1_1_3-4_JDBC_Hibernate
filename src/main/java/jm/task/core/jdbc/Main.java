package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
	public static void main(String[] args) {
		UserDao userDao = new UserDaoJDBCImpl();

		userDao.createUsersTable();

		userDao.saveUser("Ivan", "Ivanov", (byte) 5);
		userDao.saveUser("Ivan", "Ivanov", (byte) 5);
		userDao.saveUser("Ivan", "Ivanov", (byte) 5);
		userDao.saveUser("Ivan", "Ivanov", (byte) 5);

		userDao.removeUserById(1);

		userDao.getAllUsers().stream().forEach(System.out::println);

		userDao.cleanUsersTable();

		userDao.dropUsersTable();

		System.out.println("OK");
	}
}
