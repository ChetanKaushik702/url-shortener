package com.ck.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ck.shortener.domain.dto.ClickStats;
import com.ck.shortener.domain.entity.ClickEvent;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long>{
    
    @Query("""
            SELECT new com.ck.shortener.domain.dto.ClickStats(c.shortCode, COUNT(c))
            FROM ClickEvent c
            WHERE c.shortCode = :code
            GROUP BY c.shortCode
            """)
    ClickStats aggregate(String code);

}
