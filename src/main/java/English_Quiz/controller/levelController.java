package English_Quiz.controller;

import English_Quiz.repository.levelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class levelController {
    private final levelRepository levelRepo;

    public levelController(levelRepository levelRepo) {
        this.levelRepo = levelRepo;
    }
    @GetMapping("/category/{id}")
    public String levels(@PathVariable int id, Model model) {
        model.addAttribute("levels", levelRepo.findByCategoryId(id));
        return "levels";
    }
}