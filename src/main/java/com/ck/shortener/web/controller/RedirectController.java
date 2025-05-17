package com.ck.shortener.web.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ck.shortener.service.UrlShortenerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RedirectController {
    
    private final UrlShortenerService service;

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code, HttpServletRequest request) {
        URI target = service.registerClick(code, request);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(target);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
