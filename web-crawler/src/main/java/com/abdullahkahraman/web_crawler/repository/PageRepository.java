package com.abdullahkahraman.web_crawler.repository;

import com.abdullahkahraman.web_crawler.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
}
