package com.InternetShopIberia.service;

import com.InternetShopIberia.model.User;
import com.InternetShopIberia.repository.UserRepository;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShopUsersDetailService implements UserDetailsService {
    @Inject
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User with the username %s doesn't exist", username));
        }
        // Create a granted authority based on user's role.
        // Can't pass null authorities to user, hence initialize with an empty arraylist
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user.isAdmin()) {
            authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        }
        // Create a UserDetails object from the data
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return userDetails;
    }
}
