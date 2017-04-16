package classes.model.behavior.storages.impl;

import classes.hibernateUtil.HibernateUtil;
import classes.model.Service;
import classes.model.behavior.storages.ServiceStorage;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;
public class ServiceStorageHibernate implements ServiceStorage {

    @Override
    public List<Service> getAllServices() {
        List<Service> results = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Service  ORDER BY SERVICE_TYPE";
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
    public Service getServiceById(int serviceId) {
        Service result = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Service WHERE id= :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", serviceId);
            result = (Service) query.list().get(0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
        return result;
    }

    @Override
    public void storeService(Service service) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(service);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
    }

    @Override
    public void deleteService(int serviceId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String hql = "DELETE FROM Service " +
                    "WHERE id = :service_id";
            Query query = session.createQuery(hql);
            query.setParameter("service_id", serviceId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }

    }


    @Override
    public List<Service> getServicesBySameType(int activeServiceId) {
        List<Service> results = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Service where type=(Select type from Service where id =(Select serviceId " +
                    "from ActiveService where id=:id )) and (id<>(Select serviceId from ActiveService  where id=:id))";
                Query query = session.createQuery(hql);
            query.setParameter("id", activeServiceId);
            results = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))

                session.close();
        }
        return results;
    }
}