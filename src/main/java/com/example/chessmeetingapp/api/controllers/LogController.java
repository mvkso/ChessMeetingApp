package com.example.chessmeetingapp.api.controllers;



import com.example.chessmeetingapp.api.response.LogResponse;
import com.example.chessmeetingapp.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/log")
public class LogController {

    private LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/getLogs")
    @PreAuthorize("hasAuthority('Admin')")
    public List<LogResponse> getAllLogs(){
        return logService.getLogs()
                .stream()
                .map(LogResponse::fromLog)
                .collect(Collectors.toList());
    }

    @GetMapping("/getLogs/{pageNumber}")
    //@PreAuthorize("hasAuthority('Admin')")
    @Transactional
    public List<LogResponse> getLogsByPage(@PathVariable("pageNumber") int pageNumber){
        return logService.getLogsByPage(pageNumber,10)
                .stream()
                .map(LogResponse::fromLog)
                .collect(Collectors.toList());
    }
}
