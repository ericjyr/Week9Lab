package dataaccess;

import javax.persistence.EntityManager;
import models.Role;


public class RoleDB {
    
    
    public Role get(int roleId) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
     
        try {
            Role role = em.find(Role.class, roleId);
            return role;
        }
        finally {
            em.close();
        }
    }    
}
    

    
    





