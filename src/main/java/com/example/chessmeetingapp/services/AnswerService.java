package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.Answer;
import com.example.chessmeetingapp.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> getAnswersByUserCreatorId(int id){
        List<Answer> answerList = new ArrayList<>();
        answerRepository.findAnswerByUserCreator_IdOrderByCreatedDateDesc(id).forEach(answerList::add);

        return answerList;
    }

    public List<Answer> getAnswersByUserCreatorIdAndUseful(int id, boolean useful){
        List<Answer> answerList = new ArrayList<>();
        answerRepository.findAnswerByUserCreator_IdAndUsefulOrderByCreatedDateDesc(id, useful).forEach(answerList::add);

        return answerList;
    }

    public Answer getAnswerById(int id){
        return answerRepository.findById(id).get();
    }

}
