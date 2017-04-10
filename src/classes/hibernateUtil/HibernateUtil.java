package classes.hibernateUtil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static SessionFactory sessionFactory=null;

    private HibernateUtil() {
    }

    static {
        try {
            sessionFactory=new Configuration().configure().buildSessionFactory();

            //    sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            // sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);


        }

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


}

/*
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

public class HibernateUtil {


  private static final String PERSISTENT_UNIT_NAME = "org.hibernate.ejb.HibernatePeristence";

    private static final EntityManagerFactory emFactory;
    static {
        try {

            emFactory = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static CriteriaBuilder getCriteriaBuilder() {
        System.out.println("build");
        CriteriaBuilder builder = emFactory.getCriteriaBuilder();

        return builder;
    }

    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }


}


*/