package com.example.chessmeetingapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(nullable = false, length = 1024)
    private String content;

    @Column(nullable = false, length = 16)
    private String category;

    private int answersCount = 0;

    private LocalDateTime createdDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userCreator")
    @JsonBackReference
    private UserDetails userCreator;

    @OneToMany(mappedBy = "topic")
    @JsonManagedReference
    private List<Answer> answers;

    public Topic() {
    }

    public Topic(String title, String content, String category, LocalDateTime createdDate, UserDetails userDetails) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdDate = createdDate;
        this.userCreator = userDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public UserDetails getUserDetails() {
        return userCreator;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userCreator = userDetails;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(int answersCount) {
        this.answersCount = answersCount;
    }

    public String displayParsedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }

}
