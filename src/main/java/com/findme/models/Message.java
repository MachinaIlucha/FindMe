package com.findme.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "TABLE_MESSAGE")
public class Message {
    private Long id;
    private String text;
    private Date dateSent;
    private Date dateRead;
    private User userFrom;
    private User userTo;


    /*****************************************Getters******************************************************************/

    @Id
    @SequenceGenerator(name = "PR_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PR_SEQ")
    @Column(name = "MESSAGE_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "TEXT")
    public String getText() {
        return text;
    }

    @Column(name = "DATESENT")
    public Date getDateSent() {
        return dateSent;
    }

    @Column(name = "DATEREAD")
    public Date getDateRead() {
        return dateRead;
    }

    @OneToOne
    @JoinColumn(name = "USERFROM")
    public User getUserFrom() {
        return userFrom;
    }

    @OneToOne
    @JoinColumn(name = "USERTO")
    public User getUserTo() {
        return userTo;
    }


    /***********************************Setters************************************************************************/

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }
}
