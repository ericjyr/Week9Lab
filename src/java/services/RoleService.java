/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.RoleDB;
import models.Role;

/**
 *
 * @author ericr
 */
public class RoleService {
    
    public Role get(int roleId) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleId);
        return role;
    }
    
}
