package com.findme.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TABLE_POST")
public class Post {
    private Long id;
    private String message;
    private Date datePosted;
    private User userPosted;
    //TODO
    //levels permissions

    //TODO
    //comments

    /*****************************************Getters******************************************************************/

    @Id
    @SequenceGenerator(name = "PR_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PR_SEQ")
    @Column(name = "POST_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    @Column(name = "DATEPOSTED")
    public Date getDatePosted() {
        return datePosted;
    }

    @OneToOne
    @JoinColumn(name = "USER_ID")
    public User getUserPosted() {
        return userPosted;
    }


    /***********************************Setters************************************************************************/

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setUserPosted(User userPosted) {
        this.userPosted = userPosted;
    }
}
