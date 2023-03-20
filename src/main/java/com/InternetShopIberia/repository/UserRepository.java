package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
