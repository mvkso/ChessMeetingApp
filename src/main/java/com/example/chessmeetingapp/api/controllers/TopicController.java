package com.example.chessmeetingapp.api.controllers;

import com.example.chessmeetingapp.api.response.MessageResponse;
import com.example.chessmeetingapp.entities.Answer;
import com.example.chessmeetingapp.entities.Topic;
import com.example.chessmeetingapp.requests.forum.AddAnswer;
import com.example.chessmeetingapp.requests.forum.CreateTopic;
import com.example.chessmeetingapp.requests.forum.UpdateAnswer;
import com.example.chessmeetingapp.services.AnswerService;
import com.example.chessmeetingapp.services.TopicService;
import com.example.chessmeetingapp.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

import static com.example.chessmeetingapp.error.ErrorMessage.errorMessage;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final UserDetailsService userDetailsService;
    private final TopicService topicService;
    private final AnswerService answerService;

    @Autowired
    public TopicController(UserDetailsService userDetailsService, TopicService topicService, AnswerService answerService) {
        this.userDetailsService = userDetailsService;
        this.topicService = topicService;
        this.answerService = answerService;
    }


    @GetMapping("/{id}")
    public Topic displayTopic(@PathVariable("id") int id){
        return topicService.getTopicById(id).get();
    }

    @PutMapping("/answer/{id}")
    public ResponseEntity<?> updateAnswer(@Valid @RequestBody UpdateAnswer updateAnswer, @PathVariable("id") int id){
        try{
            topicService.updateAnswer(id, updateAnswer.answerId(), updateAnswer.action());
            return ResponseEntity.ok(new MessageResponse("Answer id: "+updateAnswer.answerId()+" for topic: " + id + " updated successfully!"));
        }catch (NoSuchElementException ex){
            return errorMessage("Error in updateAnswer function!");
        }

    }

    @PostMapping("/answer")
    public ResponseEntity<?> addAnswer(@Valid @RequestBody AddAnswer addAnswer){
        topicService.addAnswer(addAnswer.topicId(), addAnswer.userId(), addAnswer.content());
        return ResponseEntity.ok(new MessageResponse("Answer '"+addAnswer.content()+"' of userId: "+addAnswer.userId()+" for topicId: "+addAnswer.topicId()+" added!"));
    }


    @PostMapping("/")
    public ResponseEntity<?> createTopic(@Valid @RequestBody CreateTopic createTopic){
        topicService.createTopic(createTopic.title(),createTopic.content(),createTopic.category(),createTopic.userId());
        return ResponseEntity.ok(new MessageResponse("Topic title: "+createTopic.title()+" for category '"+createTopic.category()+"' of userId: "+createTopic.userId()+" added!"));
    }
}
