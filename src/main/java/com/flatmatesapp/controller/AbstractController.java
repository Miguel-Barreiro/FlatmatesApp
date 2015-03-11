package com.flatmatesapp.controller;

import com.flatmatesapp.security.PersonUserDetails;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public abstract class AbstractController implements Serializable {
    private static final long serialVersionUID = 19784657845612384L;
    
    @Autowired
    protected GlobalManager globalManager;
    
    @Autowired
    protected SessionManager sessionManager;
    
    protected PersonUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return (PersonUserDetails) auth.getPrincipal();
    }
    
}