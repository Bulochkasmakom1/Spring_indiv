package com.example.crm.data.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="participant")
public class Participant {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    @NotBlank
    String child;
    @Column(name = "dateBirth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    java.util.Date dateBirth;
    @Column
    String city;
    @Column
    String country;
    @Column
    String parent;
    @Column
    String emailAddress;
    @Column
    String phoneNumber;

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public java.util.Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(java.util.Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
