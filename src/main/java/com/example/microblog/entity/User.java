package com.example.microblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@Getter
@Setter
@Scope("session")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="USER_ID")
    private int id;
    @Column(name = "JOIN_DATE")
    private String joinDate;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "USER_PASSWORD")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER)
    private List<Role> roles;


    public void addPost(Post post){
        if(posts == null) this.posts = new LinkedList<>();
        this.posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post){
        this.posts.remove(post);
        post.setUser(null);
    }

    public void addRole(Role role){
        if(roles == null) this.roles = new LinkedList<>();
        this.roles.add(role);
        role.setUser(this);
    }

    public void removeRole(Role role){
        this.roles.remove(role);
        role.setUser(null);
    }

    public User(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof  User)) return false;
        User user = (User) obj;
        return this.email.equals(user.email) &&
                this.username.equals(user.username) &&
                this.password.equals(user.password);
    }

    @Override
    public String toString() {
        return "User{" +
                ", joinDate='" + joinDate + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +
                '}';
    }
}

