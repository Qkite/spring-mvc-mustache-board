package com.example.springmvcmustacheboard.domain.dto;

import com.example.springmvcmustacheboard.domain.Article;
import lombok.Getter;

@Getter
public class ArticleDto {
    private Long id;

    private String title;
    private String content;



    public ArticleDto(String title, String content) {
        this.title= title;
        this.content = content;
    }

    public Article toEntity(){
        return new Article(title, content);
    }
}
