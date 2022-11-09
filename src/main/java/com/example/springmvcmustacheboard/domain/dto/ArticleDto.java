package com.example.springmvcmustacheboard.domain.dto;

import com.example.springmvcmustacheboard.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// Setter와 Constructor를 모두 선언하거나
// Controller에서 Dto에 @ModelAttribute를 붙여줘서 설정할 수 있음

@Getter
//@NoArgsConstructor
//@Setter
//@AllArgsConstructor
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

    public Article toEntity(Long id){
        return new Article(id, title, content);
    }


}
