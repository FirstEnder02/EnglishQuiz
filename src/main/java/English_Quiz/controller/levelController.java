package English_Quiz.controller;

import English_Quiz.repository.LevelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LevelController {
    private final LevelRepository levelRepo;

    public LevelController(LevelRepository levelRepo) {
        this.levelRepo = levelRepo;
    }

    @GetMapping("/category/{id}")
    public String levels(@PathVariable int id, Model model) {
        model.addAttribute("categoryId", id);
        model.addAttribute("levels", levelRepo.findByCategoryId(id));
        return "levels";
    }
}