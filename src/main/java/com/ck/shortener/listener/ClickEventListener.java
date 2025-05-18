package com.ck.shortener.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ck.shortener.domain.entity.ClickEvent;
import com.ck.shortener.domain.event.ClickCapturedEvent;
import com.ck.shortener.repository.ClickEventRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickEventListener {
    
    private final ClickEventRepository repo;

    @Async
    @EventListener
    public void handle(ClickCapturedEvent evt) {
        HttpServletRequest req = evt.request();

        try {
            ClickEvent ce = new ClickEvent();
            ce.setShortCode(evt.shortCode());
            ce.setIpAddress(req.getRemoteAddr());
            ce.setUserAgent(req.getHeader("User-Agent"));
            ce.setReferrer(req.getHeader("Referer"));
            repo.save(ce);
    
            log.debug("Saved click for {}", evt.shortCode());
        } catch (Exception e) {
            log.error("Could not persist click for {}: {}", evt.shortCode(), e.getMessage());
        }

    }
}
