package classes.model.behavior.storages.impl;

import classes.hibernateUtil.HibernateUtil;
import classes.model.User;
import classes.model.UserParams;
import classes.model.behavior.storages.UserStorage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserStorageHibernate implements UserStorage {
    @Override
    public void storeUser(User user) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
    }


    @Override
    public User getUserById(int id) {
        User result = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM User WHERE id= :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            result = (User) query.list().get(0);
            System.out.println("getById");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
        return result;
    }

    @Override
    public User getUserByLogin(String login) {
        User result = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM User WHERE login = :login";
            Query query = session.createQuery(hql);
            query.setParameter("login", login);
            result = (User) query.list().get(0);
            System.out.println("getByLogin");
            System.out.println(result.getName() + result.getLogin());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
        return result;

    }

    public User getUser(String login, String password) {
        User result = null;
        Session session = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM User WHERE login = :login and password=:password";
            Query query = session.createQuery(hql);
            query.setParameter("login", login);
            query.setParameter("password", password);
            result = (User) query.list().get(0);
            System.out.println("getByLandP");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
        return result;

    }

    @Override
    public List<User> getAllUsers() {
        List<User> results = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM User ";
            Query query = session.createQuery(hql);
            results = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
        return results;
    }



    @Override
    public void deleteUser(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String hql = "DELETE FROM User " +
                    "WHERE id = :user_id";
            Query query = session.createQuery(hql);
            query.setParameter("user_id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
    }

    @Override
    public List<User> searchUsersByParams(UserParams userParams) {
        return null;
    }
    @Override
    public List<User> searchUsersBySubparams(UserParams userParams) {
        return null;
    }
}