package com.example.chessmeetingapp.api.controllers;


import com.example.chessmeetingapp.entities.Answer;
import com.example.chessmeetingapp.services.AnswerService;
import com.example.chessmeetingapp.services.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answers")
public class AnswersController {

    public static final Logger logger = LoggerFactory.getLogger(AnswersController.class);
    private final AnswerService answerService;


    @Autowired
    public AnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{userId}")
    public List<Answer> displayAnswersByUser(@PathVariable("userId") int id){
        return answerService.getAnswersByUserCreatorId(id)
                .stream().collect(Collectors.toList());
    }

    @GetMapping("/{userId}/useful")
    public List<Answer> displayUsefulAnswers(@PathVariable("userId") int id){
        return answerService.getAnswersByUserCreatorIdAndUseful(id, true)
                .stream().collect(Collectors.toList());
    }
}
