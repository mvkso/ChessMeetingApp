package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.Log;
import com.example.chessmeetingapp.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void saveLog(Log log){
        logRepository.save(log);
    }

    public List<Log> getLogs() {
        List<Log> logs=new LinkedList<>();
        logRepository.findAll().forEach(logs::add);
        return logs;
    }

    public Page<Log> getLogsByPage(int pageNumber, int size) {
        Pageable logs= PageRequest.of(pageNumber, size, Sort.by("date").descending());
        return logRepository.findAll(logs);
    }

}
