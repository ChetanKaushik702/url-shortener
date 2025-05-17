package com.ck.shortener.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ck.shortener.service.UrlShortenerService;
import com.ck.shortener.web.dto.ShortenRequest;
import com.ck.shortener.web.dto.ShortenResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class UrlApiController {
    
    private final UrlShortenerService service;

    @PostMapping
    public ShortenResponse shorten(@Valid @RequestBody ShortenRequest req) {
        var m = service.create(req.url(), req.expiresAt());
        return new ShortenResponse(m.getShortCode(), m.getOriginalUrl(), m.getCreateAt());
    }

    @GetMapping("/{code}/stats")
    public com.ck.shortener.domain.dto.ClickStats stats(@PathVariable String code) {
        return service.stats(code);
    }
}
