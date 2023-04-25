package com.InternetShopIberia.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "USERS")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(name="ADMIN", columnDefinition="boolean")
    @NotEmpty
    private boolean admin;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany
    private List<UserProductList> collections;

    /*@Column(name = "ROLES")
    private List<String> roles;*/
}
