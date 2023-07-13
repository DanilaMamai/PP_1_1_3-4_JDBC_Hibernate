package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `user` (`id` INT NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NULL, `lastname` VARCHAR(45) NULL, `age` INT(3) NULL, PRIMARY KEY (`id`), UNIQUE INDEX `idnew_table_UNIQUE` (`id` ASC) VISIBLE);";
	private static final String CLEAR_TABLE = "TRUNCATE TABLE `user`";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS `user`";

	public UserDaoHibernateImpl() {

	}

	@Override
	public void createUsersTable() {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.createSQLQuery(CREATE_TABLE).executeUpdate();

			transaction.commit();
		} catch (Exception e) {
			System.err.println("ERROR CREATE TABLE USERS");
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void dropUsersTable() {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.createSQLQuery(DROP_TABLE).executeUpdate();

			transaction.commit();
		} catch (Exception e) {
			System.err.println("ERROR DROP TABLE USERS");
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void saveUser(String name, String lastName, byte age) {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			User user = new User(name, lastName, age);

			session.save(user);

			transaction.commit();
		} catch (Exception e) {
			System.err.println("ERROR CREATE USER");
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void removeUserById(long id) {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.remove(session.load(User.class, id));

			transaction.commit();
		} catch (Exception e) {
			System.err.println("ERROR DELETE USER");
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> rootEntry = cq.from(User.class);
			CriteriaQuery<User> all = cq.select(rootEntry);
			
			TypedQuery<User> allQuery = session.createQuery(all);
			users = allQuery.getResultList();

			transaction.commit();
		} catch (Exception e) {
			System.err.println("ERROR GET USERS");
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return users;
	}

	@Override
	public void cleanUsersTable() {
		Transaction transaction = null;
		try (Session session = Util.getSessionFactory().openSession();) {
			transaction = session.beginTransaction();

			session.createSQLQuery(CLEAR_TABLE).executeUpdate();

			transaction.commit();
		} catch (Exception e) {
			System.err.println("ERROR CLEAR USERS");
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
}
