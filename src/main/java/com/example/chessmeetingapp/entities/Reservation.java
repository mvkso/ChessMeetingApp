package com.example.chessmeetingapp.entities;

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
    private UserDetails userCreator;

    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;

    private String subject;
    private String address;

    private int slotsBooked = 0;
    private int allSlots;

    @ManyToMany(mappedBy = "reservations")
    private Set<UserDetails> usersReserved = new HashSet<>();


    public Reservation() {
    }

    public Reservation(UserDetails userCreator, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, String subject, String address, int allSlots) {
        this.userCreator = userCreator;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.subject = subject;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
