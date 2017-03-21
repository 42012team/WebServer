package classes.hibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;


public class HibernateUtil {
    private static final String PERSISTENT_UNIT_NAME = "Hibernate_JPA";

    private static final EntityManagerFactory emFactory;

    static {
        try {
            System.out.println("murk");
            emFactory = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
            System.out.println(emFactory==null);
        } catch (Throwable ex) {
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



