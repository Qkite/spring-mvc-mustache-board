package com.example.springmvcmustacheboard.controller;

import com.example.springmvcmustacheboard.domain.Article;
import com.example.springmvcmustacheboard.domain.dto.ArticleDto;
import com.example.springmvcmustacheboard.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/articles")
@Slf4j
public class ArticleController {

    // Spring이 Constructor를 통해서 ArticleRepository 구현체를 DI 해줌
    // ArticleRepository는 interface지만 그 구현체(ArticleDao)를 Springboot가 자동으로 구성을 해서 넣어줌
    // findAll(), findById(), save() 등이 들어있음
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping(value = "/new")
    public String createPage(){
        return "new";
    }



    @GetMapping(value = "/{id}")
    public String selectSingle(@PathVariable Long id, Model model){
        Optional<Article> articleOptional = articleRepository.findById(id);
        // 결과가 있을수도 없을수도 있어서 Optional로 받음
        if (!articleOptional.isEmpty()) {
            model.addAttribute("article", articleOptional.get());
            // optional한 데이터에 get을 해주어야 받아준 entity가 나옴
            return "show";
        } else {
            return "error";
        }
    }

    @GetMapping(value = "/list")
    public String list(Model model){
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articles", articleList);

        return "list";
    }

    @GetMapping(value = "")
    public String firstDisplay(){
        return "redirect:/articles/list";
    }


    @PostMapping(value = "")
    public String add(ArticleDto articleDto){
        log.info(articleDto.getTitle(), articleDto.getContent());
        Article savedArticle = articleRepository.save(articleDto.toEntity());
        log.info("generated:{}", savedArticle.getId());
        return String.format("redirect:/articles/%d", savedArticle.getId());
    }

}
