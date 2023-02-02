/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author ericr
 */
public class Role implements Serializable {
    private int roleID;
    private String roleName;
    
    public Role(int roleID) {
        this.roleID = roleID;
        roleName = roleID == 1 ? "system admin" :
                                 "regular user";
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
    

}
