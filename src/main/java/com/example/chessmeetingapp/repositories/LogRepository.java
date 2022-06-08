package com.example.chessmeetingapp.repositories;

import com.example.chessmeetingapp.entities.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LogRepository extends CrudRepository<Log, Integer>, PagingAndSortingRepository<Log, Integer> {
}
