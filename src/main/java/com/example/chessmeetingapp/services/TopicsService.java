package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.Reservation;
import com.example.chessmeetingapp.entities.Topic;
import com.example.chessmeetingapp.entities.UserDetails;
import com.example.chessmeetingapp.repositories.AnswerRepository;
import com.example.chessmeetingapp.repositories.TopicRepository;
import com.example.chessmeetingapp.repositories.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TopicsService {

    private final UserDetailsRepository userDetailsRepository;
    private final TopicRepository topicRepository;
    private final AnswerRepository answerRepository;
    private final UserDetailsService userDetailsService;

    @PersistenceContext
    EntityManager em;

    public static final Logger logger = LoggerFactory.getLogger(TopicsService.class);

    @Autowired
    public TopicsService(TopicRepository topicRepository, UserDetailsRepository userDetailsRepository, AnswerRepository answerRepository, UserDetailsService userDetailsService) {
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userDetailsService = userDetailsService;
    }


    public List<Topic> getAllTopics(){
        var result = new ArrayList<Topic>();
        try{
            topicRepository.findAllByOrderByCreatedDateDesc().forEach(result::add);
        }catch(NoSuchElementException e){
            logger.warn("get all topics error ");
            return List.of();
        }

        return result;
    }

    public List<Topic> getAllTopicsByCategory(String category){
        var result = new ArrayList<Topic>();
        try{
            topicRepository.findTopicsByCategoryOrderByCreatedDateDesc(setHeader(category)).forEach(result::add);
        }catch (Exception e){
            logger.warn("Exception in get all topics by category fun");
            return List.of();
        }
        return result;
    }

    public List<Topic> getAllTopicsByUserId(int userId){
        UserDetails userDetails;
        var result = new ArrayList<Topic>();
        try{
            userDetails = userDetailsRepository.findUserDetailsByUser_UserId(userId).get();
            topicRepository.findTopicsByUserCreator_IdOrderByCreatedDateDesc(userDetails.getId()).forEach(result::add);
        }catch (Exception e){
            logger.warn("Exception in get all topics by category fun");
            return List.of();
        }
        return result;
    }






    public String setHeader(String category){
        switch (category) {
            case "news":
                return "Chess news";
            case "rule":
                return "Chess rules";
            case "site":
                return "Chess websites";
            case "books":
                return "Chess books";
            case "match":
                return "Chess matches";
            case "history":
                return "Chess history";
            case "off":
                return "Off-topic";
            case "meet":
                return "Chess meetings";
            case "tournament":
                return "Chess tournaments";
            case "learn":
                return "Learning chess";
            default:
                return "User's topics";
        }
    }
}
