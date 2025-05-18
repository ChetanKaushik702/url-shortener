package com.ck.shortener.service;

import java.net.URI;
import java.time.Instant;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ck.shortener.domain.dto.ClickStats;
import com.ck.shortener.domain.entity.UrlMapping;
import com.ck.shortener.domain.event.ClickCapturedEvent;
import com.ck.shortener.repository.ClickEventRepository;
import com.ck.shortener.repository.UrlMappingRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "urlMappings")
public class UrlShortenerService {
    
    private final UrlMappingRepository mappingRepo;
    private final ClickEventRepository clickRepo;
    private final ShortCodeGenerator codeGen;
    private final ApplicationEventPublisher publisher;

    @Cacheable(key = "#code")
    public UrlMapping getByCode(String code) {
        return mappingRepo.findByShortCode(code).orElseThrow(() -> new IllegalArgumentException("Code not found"));
    }

    @CacheEvict(key = "#result.shortCode")
    public UrlMapping create(String url, Instant expiry) {
        for (int i = 0; i < 5; i++) {
            try {
                UrlMapping m = new UrlMapping();
                m.setOriginalUrl(url);
                m.setShortCode(codeGen.next());
                m.setExpiresAt(expiry);
                return mappingRepo.save(m);
            } catch (DataIntegrityViolationException dup) {
                // shortCode unique constraint hit — retry
            }
        }
        throw new IllegalStateException("Unable to generate unique short code after 5 attempts");
    }

    @Transactional
    public URI registerClick(String code, HttpServletRequest req) {
        UrlMapping m = getByCode(code);
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            publisher.publishEvent(new ClickCapturedEvent(m.getShortCode(), req));
        }
        return URI.create(m.getOriginalUrl());
    }

    public ClickStats stats(String code) {
        return clickRepo.aggregate(code);
    }
}
