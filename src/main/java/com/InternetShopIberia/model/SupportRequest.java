package com.InternetShopIberia.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SUPPORT_REQUEST")
@Setter
@Getter
public class SupportRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private Long code;

    @OneToOne
    private User user;

    @Column(length = 255)
    private String subject;

    @Column(length = 255)
    private String email;

    @Column(length = 1000)
    private String message;
}
