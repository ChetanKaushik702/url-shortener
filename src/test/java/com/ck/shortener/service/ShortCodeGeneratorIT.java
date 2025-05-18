package com.ck.shortener.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortCodeGeneratorIT {
    
    @Autowired
    ShortCodeGenerator generator;

    @Test
    void generatesMonotonicUniqueCodes() {
        String a = generator.next();
        String b = generator.next();
        assertNotEquals(a, b);
        assertTrue(a.compareTo(b) < 0); 
    }
}
