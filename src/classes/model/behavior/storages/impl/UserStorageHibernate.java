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
    /*@Override
     public void storeUser(User user) {
         EntityManager entityManager = null;
         try {
             entityManager = HibernateUtil.getEntityManager();
             entityManager.getTransaction().begin();
             entityManager.merge(user);
             entityManager.getTransaction().commit();
         } catch (Exception ex) {
             System.out.println("Exception occured!");
             StackTraceElement[] stackTraceElements = ex.getStackTrace();
             for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                 System.out.println(stackTraceElements[i].toString());
             }
         } finally {
             entityManager.close();
         }
     }


     @Override
     public User getUserById(int id) {
         User result = null;
         EntityManager entityManager = null;
         try {
             entityManager = HibernateUtil.getEntityManager();
             result = (User) entityManager.find(User.class, id);
         } catch (Exception ex) {
             System.out.println("Exception occured!");
             StackTraceElement[] stackTraceElements = ex.getStackTrace();
             for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                 System.out.println(stackTraceElements[i].toString());
             }
         } finally {
             try{
             entityManager.close();}
             catch (Exception ex){
                 ex.printStackTrace();
             }
         }
         return result;
     }

     @Override
     public User getUserByLogin(String login) {
         User result = null;
         EntityManager em = null;
         try {
             CriteriaBuilder builder = HibernateUtil.getCriteriaBuilder();
             em = HibernateUtil.getEntityManager();
             CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
             Root<User> userRoot = criteriaQuery.from(User.class);
             criteriaQuery.select(userRoot);
             criteriaQuery.where(builder.equal(userRoot.get("login"), login));
             result = em.createQuery(criteriaQuery).getSingleResult();
         } catch (Exception ex) {
             System.out.println("Exception occured!");
             StackTraceElement[] stackTraceElements = ex.getStackTrace();
             for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                 System.out.println(stackTraceElements[i].toString());
             }
         } finally {
             em.close();
         }
         return result;
     }
 */
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
            result = (User) session.load(User.class, id);
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
            Criteria cr = session.createCriteria(User.class);
            cr.add(Restrictions.eq("login", login));
            result = (User) cr.list().get(0);
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

            Criterion criterionLogin = Restrictions.eq("login", login);
            Criterion criterionPassword = Restrictions.eq("password", password);
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria cr = session.createCriteria(User.class);
            LogicalExpression andExp = Restrictions.and(criterionLogin, criterionPassword);
            cr.add(andExp);
            result = (User) cr.list().get(0);
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
        return null;
    }



    @Override
    public void deleteUser(int id) {

    }

    @Override
    public List<User> searchUsersByParams(UserParams userParams) {
        return null;
    }

    /*  public User getUser(String login, String password) {
          User result = null;
          EntityManager em = null;
          try {
              CriteriaBuilder builder = HibernateUtil.getCriteriaBuilder();
              em = HibernateUtil.getEntityManager();
              CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
              Root<User> userRoot = criteriaQuery.from(User.class);
              Predicate userRestriction = builder.and(
                      builder.equal(userRoot.get("login"), login),
                      builder.equal(userRoot.get("password"), password)
              );
              criteriaQuery.select(userRoot);
              criteriaQuery.where(userRestriction);
              result = em.createQuery(criteriaQuery).getSingleResult();

          } catch (Exception ex) {
              System.out.println("Exception occured!");
              StackTraceElement[] stackTraceElements = ex.getStackTrace();
              for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                  System.out.println(stackTraceElements[i].toString());
              }
              System.out.println(ex.getMessage());
          } finally {
              em.close();
          }
          return result;
      }
  */
    @Override
    public List<User> searchUsersBySubparams(UserParams userParams) {
        return null;
    }
}