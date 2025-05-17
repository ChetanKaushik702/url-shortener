package com.ck.shortener.web.dto;

import java.time.Instant;

public record ShortenResponse (
    String code,
    String url,
    Instant createdAt
) {  }
