package com.flatmatesapp.security;

import com.flatmatesapp.security.cripto.PasswordHash;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Custom authentication provider
 * 
 * @author idafinoiu
 * 
 */
@Service
@Scope("singleton")
public class PersonAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(PersonAuthenticationProvider.class);

    private UserDetailsService  userDetailsService;

    /**
     * @param userDetailsService
     */
    @Autowired
    @Required
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        LOG.debug("IN AUTHENTICATE");
        UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;

        String providedPrincipal = upAuth.getPrincipal().toString();
        
        if (StringUtils.isBlank(providedPrincipal)) {
            throw new BadCredentialsException("Blank credentials");
        }

        try {
            PersonUserDetails dbUser = (PersonUserDetails) this.userDetailsService.loadUserByUsername(providedPrincipal);
            if (dbUser == null) {
                throw new BadCredentialsException("Username not found");
            }
            if (dbUser.isEnabled()) {
                String cleartextProvidedPassword = upAuth.getCredentials().toString();

                if (StringUtils.isNotBlank(cleartextProvidedPassword)) {

                    String hashedProvidedPassword = PasswordHash.getPasswordHash(cleartextProvidedPassword,
                            providedPrincipal);
                    if (hashedProvidedPassword.equals(dbUser.getPassword())) {
                        // successful authentication
                        LOG.debug("Successfuly authenticated user with username = {}", providedPrincipal);
                        
                        dbUser.addAuthenticatedAuthority();
                        
                        Authentication successfulAuth = new UsernamePasswordAuthenticationToken(dbUser,
                                hashedProvidedPassword, dbUser.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(successfulAuth);
                        
                        return successfulAuth;
                    } else {
                        LOG.error("Password mismatch for user with username = {}", providedPrincipal);
                        throw new BadCredentialsException("Bad credentials");
                    }
                }
            } else {
                LOG.error("Account removed for user with username = {}", providedPrincipal);
                throw new DisabledException("Account removed");
            }
        } catch (UsernameNotFoundException e) {
            LOG.error("Username not found : {}", providedPrincipal);
        }
        
        throw new BadCredentialsException("Bad credentials");
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.security.authentication.AuthenticationProvider#supports
     * (java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
