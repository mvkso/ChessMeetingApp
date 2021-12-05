package com.example.chessmeetingapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 1024)
    private String content;

    @Column(nullable = false)
    private boolean useful;

    private LocalDateTime createdDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userCreator")
    @JsonBackReference
    private UserDetails userCreator;

    @ManyToOne
    @JoinColumn(name = "topic")
    @JsonBackReference
    private Topic topic;


    public String displayParsedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }


    public String displayBeginning() {
        return (this.content.length() < 32) ? this.content.concat("...") : this.content.substring(0, 30).concat("...");
    }

    public Answer() {
    }

    public Answer(String content, LocalDateTime createdDate, UserDetails userCreator) {
        this.content = content;
        this.createdDate = createdDate;
        this.userCreator = userCreator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public UserDetails getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(UserDetails userCreator) {
        this.userCreator = userCreator;
    }

    public boolean isUseful() {
        return useful;
    }

    public void setUseful(boolean useful) {
        this.useful = useful;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
