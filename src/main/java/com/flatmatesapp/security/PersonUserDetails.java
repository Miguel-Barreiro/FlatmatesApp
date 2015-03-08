package com.flatmatesapp.security;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom {@link UserDetails} to hold authentication data
 */
public class PersonUserDetails implements UserDetails, CredentialsContainer {

    private static final long           serialVersionUID = 3455504463254931014L;

    private final BigDecimal            id;
    private String                      username;
    private String                      password;
    private String                      fullName;
    private String                      email;
    private final Set<GrantedAuthority> authorities;
    private final boolean               enabled;
    private Date                        lastLogin;

    /**
     * Minimal constructor - these fields must be set to use this {@link UserDetails} instance
     * 
     * @param id
     * @param username
     * @param password
     * @param enabled
     * @param accountNonLocked
     * @param authorities
     */
    public PersonUserDetails(BigDecimal id, String username,
            String password, boolean enabled, boolean accountNonLocked, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;

        // negation of the "deleted" DB property
        this.enabled = enabled;

        if (authorities != null) {
            this.authorities = new HashSet<>(authorities);
        } else {
            this.authorities = new HashSet<>(0);
        }

    }

    public BigDecimal getId() {
        return id;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.CredentialsContainer#eraseCredentials()
     */
    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities ()
     */
    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableSet(this.authorities);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails# isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails# isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails# isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Adds the {@link ContractorRole} USER to this user details - signifies that the user was
     * successfully authenticated
     */
    public void addAuthenticatedAuthority() {
        this.authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        // custom logic implemented on the DB user id property
        // for the time being, an authenticated principal can change his user name
        // use BigIntegers to avoid scaling mismatch (such as 2.00 doesn't equal 2.0)
        if (obj instanceof PersonUserDetails) {
            return id.toBigInteger().equals(((PersonUserDetails) obj).id.toBigInteger());
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        // custom hash code override based on the equals contract
        return id.toBigInteger().hashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // exclude the "password" field from the String representation
        return ReflectionToStringBuilder.toStringExclude(this, "password");
    }

}
