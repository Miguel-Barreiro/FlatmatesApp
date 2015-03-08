package com.flatmatesapp.security;

import com.flatmatesapp.jparepos.UserRepository;
import com.flatmatesapp.model.User;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom {@link UserDetailsService} implementation
 * 
 */
@Service("userDetailsService")
@Scope("singleton")
@Transactional
@Slf4j
public class PersonUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String username) {

        @SuppressWarnings("unchecked")
        List<User> users = this.userRepository.findByUsername(username);
        User user = null;
        if (users == null || users.isEmpty()) {
            return null;
        }
        if (users.size() > 0) {
            if (users.size() > 1) {
                log.warn("Multiple users found with username: " + username);
            }
            user = users.get(0);
        }

        if (user != null) {

            Set<GrantedAuthority> authorities = new HashSet<>(1);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            boolean userEnabled = true;
            PersonUserDetails userDetails = new PersonUserDetails(new BigDecimal(user.getId()), 
                    user.getUsername(), user.getPassword(), true, userEnabled, authorities);

            userDetails.setFullName(user.getFirstName() + " " + user.getLastName());
            userDetails.setEmail(user.getEmail());
            user.setLastLogin(user.getCurrentLogin());
            user.setCurrentLogin(new Date());
            userDetails.setLastLogin(user.getLastLogin());
            this.userRepository.save(user);
            return userDetails;
        }

        throw new UsernameNotFoundException("No user found for the provided username: " + username);
    }

}
