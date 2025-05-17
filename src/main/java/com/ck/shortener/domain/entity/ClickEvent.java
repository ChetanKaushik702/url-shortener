package com.ck.shortener.domain.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "click_events", indexes = @Index(columnList = "shortCode"))
@Getter @Setter @NoArgsConstructor
public class ClickEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortCode;

    private String ipAddress;
    private String userAgent;
    private String referrer;

    @CreationTimestamp
    private Instant clickedAt;
    
}
