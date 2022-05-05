package com.example.microblog.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admins")
@NoArgsConstructor
@Getter
@Setter
public class Admin {
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "admin_email")
    private String email;

    public Admin(String email){
        this.email = email;
    }

}
