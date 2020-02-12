package com.findme.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TABLE_USER")
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    //TODO from existed data
    private String country;
    private String city;

    private Integer age;
    private Date dateRegistered;
    private Date dateLastActive;
    //TODO enum
    private String relationshipStatus;
    private String religion;
    //TODO from existed data
    private String school;
    private String university;
    private String email;
    private String password;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "USER_MESSAGESSENT",
//            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID", referencedColumnName = "id"))
//    private List<Message> messagesSent;
//
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "USER_MESSAGESRECEIVED",
//            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID", referencedColumnName = "id"))
//    private List<Message> messagesReceived;

    //private String[] interests;

    /*****************************************Getters******************************************************************/

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="users_seq")
    @SequenceGenerator(name="users_seq",
            sequenceName="SEQ_USER", allocationSize=10)
    @Column(name = "USER_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "FIRSTNAME")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "LASTNAME")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    @Column(name = "AGE")
    public Integer getAge() {
        return age;
    }

    @Column(name = "DATEREGISTERED")
    public Date getDateRegistered() {
        return dateRegistered;
    }

    @Column(name = "DATELASTACTIVE")
    public Date getDateLastActive() {
        return dateLastActive;
    }

    @Column(name = "RELATIONSHIPSTATUS")
    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    @Column(name = "RELIGION")
    public String getReligion() {
        return religion;
    }

    @Column(name = "SCHOOL")
    public String getSchool() {
        return school;
    }

    @Column(name = "UNIVERSITY")
    public String getUniversity() {
        return university;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    //    public List<Message> getMessagesSent() {
//        return messagesSent;
//    }
//
//    public List<Message> getMessagesReceived() {
//        return messagesReceived;
//    }


    /***********************************Setters************************************************************************/

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setDateLastActive(Date dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //    public void setMessagesSent(List<Message> messagesSent) {
//        this.messagesSent = messagesSent;
//    }
//
//    public void setMessagesReceived(List<Message> messagesReceived) {
//        this.messagesReceived = messagesReceived;
//    }
}
