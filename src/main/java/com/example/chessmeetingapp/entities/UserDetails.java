package com.example.chessmeetingapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @JoinColumn(name = "userId")
    private User user;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "booked_reservations",
            joinColumns = @JoinColumn(name = "userDetails_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    @JsonBackReference
    private Set<Reservation> bookedReservations;



    @OneToMany(mappedBy = "userCreator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Reservation> createdReservations;

    @OneToMany(mappedBy = "userCreator", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Topic> topics;

    @OneToMany(mappedBy = "userCreator", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Answer> answers;


    public UserDetails() {
    }

    public UserDetails(int id, String firstName, String lastName, String phoneNumber, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.user = user;

    }

    public UserDetails(String firstName, String lastName, String phoneNumber, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    public void addBookedReservation(Reservation reservation){
        this.bookedReservations.add(reservation);
    }

    public void removeBookedReservation(Reservation reservation){
        this.bookedReservations.remove(reservation);
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
        return bookedReservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.bookedReservations = reservations;
    }

    public Set<Reservation> getCreatedReservations() {
        return createdReservations;
    }

    public void setCreatedReservations(Set<Reservation> createdReservations) {
        this.createdReservations = createdReservations;
    }

    public Set<Reservation> getBookedReservations() {
        return bookedReservations;
    }

    public void setBookedReservations(Set<Reservation> bookedReservations) {
        this.bookedReservations = bookedReservations;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", user=" + user +
                ", reservations=" + bookedReservations +
                '}';
    }
}
