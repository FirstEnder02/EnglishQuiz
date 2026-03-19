package English_Quiz.controller;

import English_Quiz.model.Category;
import English_Quiz.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    private final CategoryRepository categoryRepo;

    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Category> categories = new ArrayList<>();
        categoryRepo.findAll().forEach(categories::add);
        model.addAttribute("categories", categories);
        return "categories";
    }
}