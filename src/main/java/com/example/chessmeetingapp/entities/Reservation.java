package com.example.chessmeetingapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userCreator")
    @JsonBackReference
    private UserDetails userCreator;

    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;


    private String subject;
    private String cityAddress;

    private int minimumRank;


    private int slotsBooked = 0;
    private int allSlots;

    @ManyToMany(mappedBy = "reservations", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserDetails> usersReserved = new HashSet<>();


    public Reservation() {
    }

    public Reservation(UserDetails userCreator, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, String subject, String cityAddress, int minimumRank, int allSlots) {
        this.userCreator = userCreator;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.subject = subject;
        this.cityAddress = cityAddress;
        this.minimumRank = minimumRank;
        this.allSlots = allSlots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDetails getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(UserDetails userCreator) {
        this.userCreator = userCreator;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String address) {
        this.cityAddress = address;
    }

    public int getMinimumRank() {
        return minimumRank;
    }

    public void setMinimumRank(int minimumRank) {
        this.minimumRank = minimumRank;
    }

    public int getSlotsBooked() {
        return slotsBooked;
    }

    public void setSlotsBooked(int slotsBooked) {
        this.slotsBooked = slotsBooked;
    }

    public int getAllSlots() {
        return allSlots;
    }

    public void setAllSlots(int allSlots) {
        this.allSlots = allSlots;
    }

    public Set<UserDetails> getUsersReserved() {
        return usersReserved;
    }

    public void setUsersReserved(Set<UserDetails> usersReserved) {
        this.usersReserved = usersReserved;
    }
}
