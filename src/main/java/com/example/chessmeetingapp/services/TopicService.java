package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.*;
import com.example.chessmeetingapp.repositories.AnswerRepository;
import com.example.chessmeetingapp.repositories.TopicRepository;
import com.example.chessmeetingapp.repositories.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TopicService {

    private final UserDetailsRepository userDetailsRepository;
    private final TopicRepository topicRepository;
    private final AnswerRepository answerRepository;
    private final UserDetailsService userDetailsService;
    private final TopicsService topicsService;
    private final LogService logService;

    @PersistenceContext
    EntityManager em;

    public static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    public TopicService(TopicRepository topicRepository, UserDetailsRepository userDetailsRepository,
                        AnswerRepository answerRepository, UserDetailsService userDetailsService, TopicsService topicsService, LogService logService) {
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userDetailsService = userDetailsService;
        this.topicsService = topicsService;
        this.logService = logService;
    }

    public Optional<Topic> getTopicById(int id){
        return topicRepository.findById(id);
    }

    public Optional<Boolean> createTopic(String title, String content, String category, int userId){
        UserDetails userDetails;
        try{
            userDetails = userDetailsRepository.findUserDetailsByUser_UserId(userId).get();
        }catch (NoSuchElementException ex){
            logger.warn("No such element exception in create topic function");

            return Optional.of(false);
        }

        try{
            category = topicsService.setHeader(category);
            Topic topic = new Topic(title,content,category,LocalDateTime.now(),userDetails);
            topicRepository.save(topic);
            logService.saveLog(new Log(LogType.INFO, "Topic with title "+title+" has been created by user of id: "+userId));

        }catch (Exception ex){
            logger.warn("Exception in create topic function");
            return Optional.of(false);
        }

        return Optional.of(true);

    }

    public Optional<Boolean> addAnswer(int topicId, int userId, String content) {
        Answer answer;
        UserDetails userCreator;
        Topic topic;
        try {
            userCreator = userDetailsService.getUserDetailsByUserId(userId).get();
            topic = getTopicById(topicId).get();
        } catch (NoSuchElementException e) {
            return Optional.of(false);
        }
        try {
            answer = new Answer(content, LocalDateTime.now(), userCreator);
            answer.setUseful(true);
            answer.setTopic(topic);
            answer.setUserCreator(userCreator);
            userCreator.getAnswers().add(answer);
            topic.getAnswers().add(answer);
            topic.setAnswersCount(topic.getAnswersCount()+1);

            answerRepository.save(answer);
            logService.saveLog(new Log(LogType.INFO, "Answer for topic with title id: "+topicId+" has been created by userCreator of id: "+userCreator.getId()));

        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(true);

    }

    public Optional<Boolean> updateAnswer(int topicId, int answerId, String action){
        Answer answer;
        Topic topic;
        try {
            topic = getTopicById(topicId).get();
            answer = answerRepository.findById(answerId).get();
            switch (action) {
                case "useful" :
                    answer.setUseful(!answerRepository.findById(answerId).get().isUseful());
                    em.merge(answer);
                    break;
                case "delete" :
                    topic.setAnswersCount(topic.getAnswersCount()-1);
                    answerRepository.delete(answer);
                    break;
            }
            return Optional.of(true);
        }catch (Exception e){
            logger.warn("exception in updateAnswer function");
            return Optional.of(false);
        }
    }


    @Transactional
    public Optional<Boolean> deleteTopic(int topicId){
        Topic topic;
        try{
            topic = getTopicById(topicId).get();
        }catch (NoSuchElementException e){
            logger.warn("No such element exception in deleteTopic");
            return Optional.of(false);
        }
        try{
                topicRepository.delete(topic);
            logService.saveLog(new Log(LogType.INFO, "Topic with id "+topicId+" has been deleted"));
                return Optional.of(true);
        }catch (Exception e){
            logger.warn("exception in delete topic fun");
            return Optional.of(false);
        }

    }



}
