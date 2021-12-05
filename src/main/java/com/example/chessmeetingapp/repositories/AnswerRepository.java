package com.example.chessmeetingapp.repositories;

import com.example.chessmeetingapp.entities.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {

    @Transactional
    void deleteAnswerById(int id);

    int countAnswersByUserCreator_Id(int id);
    int countAnswersByUserCreator_IdAndUseful(int id, boolean useful);
    int countAnswersByTopic_Id(int id);

    List<Answer> findAnswerByUserCreator_IdOrderByCreatedDateDesc(int id);
    List<Answer> findAnswerByUserCreator_IdAndUsefulOrderByCreatedDateDesc(int userCreator_id, boolean useful);
    List<Answer> findAnswerByTopic_Id(int topic_id);
}
