package com.ck.shortener.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSequenceShortCodeGenerator implements ShortCodeGenerator {
    
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();

    private final JdbcTemplate jdbc;

    @Override
    public String next() {
        Long id = jdbc.queryForObject("SELECT nextval('short_code_seq')", Long.class);
        return toBase62(id);
    }

    private String toBase62(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(ALPHABET.charAt((int) (value % BASE)));
            value /= BASE;
        }
        return sb.reverse().toString();
    }

}
