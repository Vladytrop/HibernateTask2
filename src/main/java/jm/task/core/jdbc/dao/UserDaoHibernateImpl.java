package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(255)," +
                    " lastname VARCHAR(255)," +
                    " age TINYINT)").executeUpdate();
            Object table = session.createSQLQuery("show tables").getResultList();
            System.out.println(table);
            Long count = session.createQuery("select count(*) from User", Long.class).getSingleResult();
            System.out.println(count);
            transaction.commit();
            System.out.println("Table created successfully");
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка при создании таблицы", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы",e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении пользователя",e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя",e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class)
                    .getResultList();
            transaction.commit();
            return users;
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка при получении списка пользователей",e);
        }
        return List.of();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from User")
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка при очистке таблицы",e);
        }
    }
}
