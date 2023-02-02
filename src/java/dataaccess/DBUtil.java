package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * For retrieving a EntityManagerFactory that is used to create
 * EntityManager object
 * @author ericr
 */
public class DBUtil {
    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("UserPU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
