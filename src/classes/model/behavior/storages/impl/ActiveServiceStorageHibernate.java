package classes.model.behavior.storages.impl;

import classes.hibernateUtil.HibernateUtil;
import classes.model.ActiveService;
import classes.model.behavior.storages.ActiveServiceStorage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ActiveServiceStorageHibernate implements ActiveServiceStorage {
    @Override
    public List<ActiveService> getActiveServicesByUserId(int userId) {
        List<ActiveService> results = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM ActiveService WHERE userId= :user_id AND ((state='NOT_READY') OR (state='READY' AND (nextActiveServiceId IS NULL OR nextActiveServiceId=0)))";
            Query query = session.createQuery(hql);
            query.setParameter("user_id", userId);
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
    public void deleteActiveService(int activeServiceId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
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
            if (!query.list().isEmpty()) {
                result = (ActiveService) query.list().get(0);
            }
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
            session.beginTransaction();
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
                if (getActiveServiceById(activeServicesList.get(i).getId()) != null) {
                    session.beginTransaction();
                    String hql = "UPDATE ActiveService set firstStatus=:firstStatus,secondStatus=:secondStatus," +
                            "date=:tdate, version=:version,state=:state where id=:id";
                    Query query = session.createQuery(hql);
                    query.setParameter("firstStatus", activeServicesList.get(i).getFirstStatus());
                    query.setParameter("secondStatus", activeServicesList.get(i).getSecondStatus());
                    query.setParameter("tdate", activeServicesList.get(i).getDate());
                    query.setParameter("version", activeServicesList.get(i).getVersion());
                    query.setParameter("state", activeServicesList.get(i).getState());
                    query.setParameter("id", activeServicesList.get(i).getId());
                    query.executeUpdate();
                    session.getTransaction().commit();
                } else {
                    session.beginTransaction();
                    session.merge(activeServicesList.get(i));
                    session.getTransaction().commit();
                }
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
            session.beginTransaction();
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
            System.out.println(currentId + " " + newId);
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String hql = "UPDATE ActiveService set nextActiveServiceId=:nextId WHERE id = :currentId";
            Query query = session.createQuery(hql);
            query.setParameter("nextId", newId);
            query.setParameter("currentId", currentId);
            query.executeUpdate();
            System.out.println("commit");
            session.getTransaction().commit();
            System.out.println("1");
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
            if (!query.list().isEmpty())
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
        List<ActiveService> results = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM ActiveService WHERE (userId=:userId)  and (serviceId IN(SELECT id FROM Service WHERE type =(SELECT type FROM Service WHERE id=:id))) ORDER BY date,nextActiveServiceId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("id", serviceId);
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
    public void changeNewTariffDate(int activeServiceId, Date date) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String hql = "UPDATE ActiveService SET date=:date WHERE id IN ((SELECT id FROM ActiveService WHERE id=:id),(SELECT id FROM ActiveService WHERE nextActiveServiceId=:id))";
            Query query = session.createQuery(hql);
            query.setParameter("date", date);
            query.setParameter("id", activeServiceId);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))
                session.close();
        }
    }

}