package com.example.microblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "POSTS")
@NoArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "POST_DATE")
    private String postDate;
    @Column(name = "POST_TEXT")
    private String postText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public Post(String postText){
        this.postText = postText;
        this.postDate = String.valueOf((Calendar.getInstance().getTime()));
    }

}