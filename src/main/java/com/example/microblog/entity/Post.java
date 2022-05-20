package com.example.microblog.entity;

import com.example.microblog.dto.UserRegistrationDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "POSTS")
@NoArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private int id;
    @Column(name = "POST_DATE")
    private String postDate;
    @Column(name = "POST_TEXT")
    private String postText;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    public Post(String postText){
        this.postText = postText;
        this.postDate = UserRegistrationDTO.currentDate();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postDate='" + postDate + '\'' +
                ", postText='" + postText + '\'' +
                '}';
    }
}