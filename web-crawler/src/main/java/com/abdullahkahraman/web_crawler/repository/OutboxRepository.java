package com.abdullahkahraman.web_crawler.repository;

import com.abdullahkahraman.web_crawler.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
}
