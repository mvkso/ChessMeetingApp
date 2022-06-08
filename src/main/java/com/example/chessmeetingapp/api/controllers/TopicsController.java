package com.example.chessmeetingapp.api.controllers;


import com.example.chessmeetingapp.entities.Topic;
import com.example.chessmeetingapp.services.AnswerService;
import com.example.chessmeetingapp.services.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicsController {

    private final TopicsService topicsService;
    private final AnswerService answerService;

    @Autowired
    public TopicsController(TopicsService topicsService, AnswerService answerService) {
        this.topicsService = topicsService;
        this.answerService = answerService;
    }

    @GetMapping("/")
    public List<Topic> displayAllTopics(){
        return topicsService.getAllTopics().stream().limit(15)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<Topic> displayTopics(){
        return topicsService.getAllTopics().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/{category}")
    public List<Topic> displayTopicsByCategory(@PathVariable("category") String category){
        return topicsService.getAllTopicsByCategory(category).stream().collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<Topic> displayTopicsByUserId(@PathVariable("userId") int id){
        return topicsService.getAllTopicsByUserId(id).stream().collect(Collectors.toList());

    }
}
