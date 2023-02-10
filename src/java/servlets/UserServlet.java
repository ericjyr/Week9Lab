package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author ericr
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        HttpSession session = request.getSession(false);
        String action = request.getParameter("action");
        
        try {
            if (action != null && !action.equals("")) {
                String email = request.getParameter("key").replaceAll(" ", "\\+");
                switch (action) {
                    case "edit":
                        User user = us.get(email);
                        session.setAttribute("selecteduser", user);
                        break;
                    case "delete":
                         us.delete(email);
                }
            } 
            session = request.getSession();
            List<User> users = us.getAll();
            if (users.isEmpty()) session.setAttribute("selecteduser", null);
            session.setAttribute("users", users);
        } 
        catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp" )
            .forward(request, response);         
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();
        HttpSession session = request.getSession();
        String action = request.getParameter("submit");
        
        if(action.equals("Cancel")) {
            session.setAttribute("selecteduser", null);
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp" )
                .forward(request, response);  
            return;
        }
        
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String password = request.getParameter("password");
        String roleId = request.getParameter("role");
        String[] params = {email,firstName,lastName,password,roleId};

        try {
            Boolean formMessageSet = false;
            for (int i = 0; i < params.length; i++) { 
                if(params[i].equals("") || params[i] == null){
                    request.setAttribute("formmessage", "All fields are required");
                    formMessageSet = true;
                }
            }
            
            List<User> users = us.getAll();
            for (int i = 0; i < users.size(); i++) {
                if (action.equals("Add user") && users.get(i).getEmail().equals(email)) {
                    request.setAttribute("formmessage", "Email is in use"); 
                    formMessageSet = true;
                }
            }
            
            if (formMessageSet) {
                request.setAttribute("email", email);
                request.setAttribute("firstname", firstName);
                request.setAttribute("lastname", lastName);
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp" )
                    .forward(request, response); 
                return;
            }
            switch (action) {
                case "Add user":
                    us.insert(email, firstName, lastName, password, Integer.parseInt(roleId));
                    break;
                case "Update":
                    us.update(email, firstName, lastName, password, Integer.parseInt(roleId));
                    session.setAttribute("selecteduser", null);
            }
            users = us.getAll();
            session.setAttribute("users", users);
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp" )
                .forward(request, response); 
        } 
        catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }



    }

}
