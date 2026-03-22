package org.example.controller;

import org.example.common.application.rest.ApplicationController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/docker", produces = "application/vnd.api.v1+json")
public class DockerController extends ApplicationController {
    @GetMapping
    public String dockerDemo(){
        return "Dockerizing Spring Boot Application";
    }
}