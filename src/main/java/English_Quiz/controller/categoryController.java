package English_Quiz.controller;

import English_Quiz.repository.categoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class categoryController {
    private final categoryRepository categoryRepo;

    public categoryController(categoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        return "index";
    }
}