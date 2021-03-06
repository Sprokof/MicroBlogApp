package com.example.microblog.entity;


import com.example.microblog.hash.MD5;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@Getter
@Setter
@Scope("session")
@SessionAttributes("user")
@Component
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "JOIN_DATE")
    private String joinDate;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "USER_PASSWORD")
    private String password;
    @Column(name = "IS_ACCEPTED")
    private boolean isAccepted;

    transient String acceptedCode;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Role> roles;


    public void addPost(Post post) {
        if (posts == null) this.posts = new LinkedList<Post>();
        this.posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        post.setUser(null);
    }

    public void addRole(Role role) {
        if (roles == null) this.roles = new HashSet<>();
        this.roles.add(role);
        role.setUser(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.setUser(null);
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isAccepted = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
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
                '}';
    }


    public boolean isAdmin(){
        return this.getRoles().stream().
                    map(Role::getRoleName).anyMatch((r) -> r.equals("ADMIN"));
    }

}
