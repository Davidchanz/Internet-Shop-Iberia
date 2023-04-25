package com.InternetShopIberia.service;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.exception.UserAlreadyExistException;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.repository.UserProductListRepository;
import com.InternetShopIberia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProductListService userProductListService;

    @Autowired
    private ProductService productService;

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (userExists(userDto.getUserName())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getUserName());
        }
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(encryptPassword(userDto.getPassword()));
        user.setUsername(userDto.getUserName());
        user.setEmail(userDto.getEmail());

        UserProductList userProductList2 = new UserProductList();
        userProductList2.setName("Favorites");
        userProductList2.setProducts(List.of(
                productService.getProductById(85L),
                productService.getProductById(56L)));
        userProductListService.save(userProductList2);

        UserProductList userProductList = new UserProductList();
        userProductList.setName("My Laptops");
        userProductList.setProducts(List.of(
                productService.getProductById(85L),
                productService.getProductById(56L),
                productService.getProductById(68L),
                productService.getProductById(80L)));
        userProductListService.save(userProductList);

        user.setCollections(List.of(userProductList, userProductList2));


        //user.setRoles(List.of("ROLE_USER"));

        return userRepository.save(user);
    }
    private boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private String encryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public User findUserByUserName(String userName){
        return userRepository.findByUsername(userName);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
