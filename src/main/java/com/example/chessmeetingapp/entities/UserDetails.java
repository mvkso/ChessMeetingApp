package com.example.chessmeetingapp.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "users_reservations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    private Set<Reservation> reservations = new HashSet<>();



    @OneToMany(mappedBy = "userCreator", cascade = CascadeType.ALL)
    private Set<Reservation> createdReservations;


    public UserDetails() {
    }

    public UserDetails(int id, String firstName, String lastName, String phoneNumber, User user, Set<Reservation> reservations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.reservations = reservations;
    }

    public UserDetails(String firstName, String lastName, String phoneNumber, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Reservation> getCreatedReservations() {
        return createdReservations;
    }

    public void setCreatedReservations(Set<Reservation> createdReservations) {
        this.createdReservations = createdReservations;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", user=" + user +
                ", reservations=" + reservations +
                '}';
    }
}
