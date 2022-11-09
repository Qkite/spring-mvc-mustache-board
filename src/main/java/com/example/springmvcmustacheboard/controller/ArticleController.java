package com.example.springmvcmustacheboard.controller;

import com.example.springmvcmustacheboard.domain.Article;
import com.example.springmvcmustacheboard.domain.dto.ArticleDto;
import com.example.springmvcmustacheboard.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value="/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (!articleOptional.isEmpty()) {
            model.addAttribute("article", articleOptional.get());
            // addAttribute를 해주지 않으면 결과를 view로 보여줄 수 없음
            return "edit";
        } else {
            model.addAttribute("message", String.format("%d 가 없습니다.", id));
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

    @GetMapping(value = "/{id}/delete")
    public String edit(@PathVariable Long id){
        articleRepository.deleteById(id);

        return "redirect:/articles";
    }


    @PostMapping(value = "")
    public String add(@ModelAttribute ArticleDto articleDto){
        log.info(articleDto.getTitle(), articleDto.getContent());
        Article savedArticle = articleRepository.save(articleDto.toEntity());
        log.info("generated:{}", savedArticle.getId());
        return String.format("redirect:/articles/%d", savedArticle.getId());
    }

    @PostMapping(value="/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute ArticleDto articleDto, Model model){
        log.info("id: {}, title: {}, content: {}", String.valueOf(articleDto.getId()), articleDto.getTitle(), articleDto.getContent());

        Article article = articleRepository.save(articleDto.toEntity(id));
        model.addAttribute("article", article);

        return String.format("redirect:/articles/%d", article.getId());

    }

}
