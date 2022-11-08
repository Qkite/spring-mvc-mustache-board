package com.example.springmvcmustacheboard.repository;

import com.example.springmvcmustacheboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
