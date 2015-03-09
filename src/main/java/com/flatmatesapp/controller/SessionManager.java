package com.flatmatesapp.controller;

import com.flatmatesapp.security.PersonUserDetails;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SessionManager implements Serializable {

    private static final long serialVersionUID = 10101020505869476L;

    private PersonUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return (PersonUserDetails) auth.getPrincipal();
    }

    public String returnUserName() {
        return getUserDetails().getUsername();
    }

    public Date returnUserLastLogin() {
        return getUserDetails().getLastLogin();
    }

    public void logout() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            response.sendRedirect(request.getContextPath() + "/j_spring_security_logout");
        } catch (IOException ex) {
            Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean hasRootRole() {
        return hasRole("ROLE_ROOT");
    }
    
    private boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream().anyMatch((auth) -> (role.equals(auth.getAuthority())));
    }

}
