package com.findme.models;


import javax.persistence.*;

@Entity
@Table(name = "TABLE_USERSTAGGED")
public class UserTagged {

    private Long id;
    private Long postId;
    private Long userId;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "users_seq")
    @SequenceGenerator(name = "users_seq",
            sequenceName = "SEQ_USER", allocationSize = 10)
    @Column(name = "ID_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "POST_ID")
    public Long getPostId() {
        return postId;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
