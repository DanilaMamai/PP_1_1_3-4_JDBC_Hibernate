package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
	public static void main(String[] args) {
		UserDao userDao = new UserDaoHibernateImpl();

		userDao.createUsersTable();

		userDao.saveUser("Ivan", "Ivanov", (byte) 5);
		userDao.saveUser("Ivan", "Ivanov", (byte) 5);
		userDao.saveUser("Ivan", "Ivanov", (byte) 5);
		userDao.saveUser("Ivan", "Ivanov", (byte) 5);

		userDao.removeUserById(1L);

		userDao.getAllUsers().stream().forEach(System.out::println);

		userDao.cleanUsersTable();

		userDao.dropUsersTable();

		System.out.println("OK");
	}
}
