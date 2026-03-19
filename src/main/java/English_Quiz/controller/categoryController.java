package English_Quiz.controller;

import English_Quiz.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    private final CategoryRepository categoryRepo;

    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        return "categories";
    }
}