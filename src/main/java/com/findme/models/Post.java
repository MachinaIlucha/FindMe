package com.findme.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TABLE_POST")
public class Post {
    private Long id;
    private String message;
    private Date datePosted;
    private String location;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "TABLE_USERSTAGGED",
            joinColumns = @JoinColumn(name = "POST_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"))
    private List<User> usersTagged;

    private User userPosted;
    private User userPagePosted;
    //TODO
    //levels permissions

    //TODO
    //comments

    /*****************************************Getters******************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "users_seq")
    @SequenceGenerator(name = "users_seq",
            sequenceName = "SEQ_USER", allocationSize = 10)
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

    @Column(name = "LOCATIONTAGGED")
    public String getLocation() {
        return location;
    }

    @OneToOne
    @JoinColumn(name = "USERPOSTED_ID")
    public User getUserPosted() {
        return userPosted;
    }

    @OneToMany
    public List<User> getUsersTagged() {
        return usersTagged;
    }

    @OneToOne
    @JoinColumn(name = "USERPAGEPOSTED_ID")
    public User getUserPagePosted() {
        return userPagePosted;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUsersTagged(List<User> usersTagged) {
        this.usersTagged = usersTagged;
    }

    public void setUserPagePosted(User userPagePosted) {
        this.userPagePosted = userPagePosted;
    }
}
