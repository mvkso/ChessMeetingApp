package com.example.chessmeetingapp.repositories;

import com.example.chessmeetingapp.entities.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

    Integer countTopicsByUserCreator_Id(int id);

    Optional<Topic> findTopicById(int id);

    List<Topic> findAllByOrderByCreatedDateDesc();

    List<Topic> findTopicsByCategoryOrderByCreatedDateDesc(String category);
    List<Topic> findTopicsByUserCreator_IdOrderByCreatedDateDesc(int id);

}
