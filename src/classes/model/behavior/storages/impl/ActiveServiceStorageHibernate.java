package classes.model.behavior.storages.impl;

import classes.hibernateUtil.HibernateUtil;
import classes.model.ActiveService;
import classes.model.User;
import classes.model.behavior.storages.ActiveServiceStorage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class ActiveServiceStorageHibernate implements ActiveServiceStorage {
    @Override
    public List<ActiveService> getActiveServicesByUserId(int userId) {
        List<ActiveService> results = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM ActiveService WHERE userId= :user_id";
            Query query = session.createQuery(hql);
            query.setParameter("user_id", userId);
            results = query.list();
            System.out.println("getById");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
        return results;
    }

    @Override
    public void deleteActiveService(int activeServiceId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "DELETE FROM ActiveService " +
                    "WHERE id = :activeService_id";
            Query query = session.createQuery(hql);
            query.setParameter("activeService_id", activeServiceId);
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
    public List<ActiveService> getAllActiveServices() {

        List<ActiveService> results = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM ActiveService WHERE (state='NOT_READY') ORDER BY id";
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
    public ActiveService getActiveServiceById(int activeServiceId) {
        ActiveService result = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM ActiveService WHERE id= :activeServiceId";
            Query query = session.createQuery(hql);
            query.setParameter("activeServiceId", activeServiceId);
            result = (ActiveService) query.list().get(0);
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
    public void deleteNextActiveServiceId(int nextId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "UPDATE ActiveService set nextActiveServiceId=:nextactiveservice_id " +
                    "WHERE nextActiveServiceId = :nextactiveserviceId";
            Query query = session.createQuery(hql);
            query.setParameter("nextactiveservice_id", null);
            query.setParameter("nextactiveserviceId", nextId);
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
    public void storeActiveServices(List<ActiveService> activeServicesList) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            for (int i = 0; i < activeServicesList.size(); i++) {
                session.beginTransaction();
                session.merge(activeServicesList.get(i));
                session.getTransaction().commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
    }

    @Override
    public void deleteActiveServicesByUserId(int userId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "DELETE FROM ActiveService " +
                    "WHERE userId = :user_id";
            Query query = session.createQuery(hql);
            query.setParameter("user_id", userId);
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
    public void setNextId(int currentId, int newId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "UPDATE ActiveService set nextActiveServiceId=:nextactiveservice_id " +
                    "WHERE id = :activeservice_id";
            Query query = session.createQuery(hql);
            query.setParameter("nextactiveservice_id", currentId);
            query.setParameter("activeservice_id", newId);
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
    public ActiveService getPreviousActiveService(int activeServiceId) {
        ActiveService result = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM ActiveService WHERE nextActiveServiceId= :activeServiceId";
            Query query = session.createQuery(hql);
            query.setParameter("activeServiceId", activeServiceId);
            result = (ActiveService) query.list().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
        return result;
    }

    @Override
    public List<ActiveService> getActiveServicesHistoryByUserId(int userId, int serviceId) {
        return null;
    }

    @Override
    public void changeNewTariffDate(int activeServiceId, Date date) {

    }

}