package EnglishQuiz.controller;

import EnglishQuiz.model.Category;
import EnglishQuiz.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        model.addAttribute("categories", categories);
        return "categories";
    }
}