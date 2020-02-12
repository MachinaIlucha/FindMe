package com.findme.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TABLE_RELATIONSHIP")
public class Relationship {

    private Long id;
    private User userFrom;
    private User userTo;
    private RelationshipType status;
    private Date dateCreated;
    private Date dateLastUpdated;


    /*****************************************Getters******************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "users_seq")
    @SequenceGenerator(name = "users_seq",
            sequenceName = "SEQ_USER", allocationSize = 10)
    @Column(name = "RELATIONSHIP_ID")
    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "USER_FROM_ID")
    public User getUserFrom() {
        return userFrom;
    }

    @ManyToOne
    @JoinColumn(name = "USER_TO_ID")
    public User getUserTo() {
        return userTo;
    }

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    public RelationshipType getStatus() {
        return status;
    }

    @Column(name = "DATECREATED")
    public Date getDateCreated() {
        return dateCreated;
    }

    @Column(name = "DATELASTUPDATED")
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    /***********************************Setters************************************************************************/

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public void setStatus(RelationshipType status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
}


