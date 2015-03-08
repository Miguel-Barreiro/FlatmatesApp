package com.flatmatesapp.login;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author iulian.dafinoiu
 */
@Component
@Scope("request")
public class LoginAction implements Serializable {
    private static final long serialVersionUID = 1879456238978978547L;
    
    public void redirectToDashboardIfLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("pages/secure/dashboard.faces");
            } catch (IOException ex) {
                Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
