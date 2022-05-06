package com.example.microblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
@NoArgsConstructor
@Data
public class Role {
    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ROLE_NAME")
    private String roleName;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Role(String roleName){
        this.roleName = roleName;
    }




}
