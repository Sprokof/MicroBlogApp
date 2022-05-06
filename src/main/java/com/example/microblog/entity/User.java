package com.example.microblog.entity;

import com.example.microblog.admin.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.Calendar;
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
    @Column(name = "IS_ACCEPTED")
    private boolean isAccepted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
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
        this.isAccepted = false;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof  User) && !(obj instanceof Admin)) return false;
        if(obj instanceof User){
            User user = (User) obj;
        return this.email.equals(user.email) &&
                this.username.equals(user.username) &&
                this.password.equals(user.password);
        }
        Admin admin = (Admin) obj;
        return this.email.equals(admin.getEmail());
    }

    public static String currentTime() {
        String[] params = Calendar.getInstance().toString().split(",");
        String hour; String minutes = params[24].
                substring(params[24].indexOf("=")+1);

        if (params[21].equals("AM_PM=1")) {
            hour = String.valueOf(Integer.parseInt(params[22].
                    substring(params[22].indexOf("=") + 1)) + 12);
        } else {
            hour = String.valueOf(Integer.parseInt(params[22].
                    substring(params[22].indexOf("=") + 1)));
        }
        if(minutes.length() == 1) minutes = "0"+minutes;
        return String.format("%s:%s", hour, minutes);
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

