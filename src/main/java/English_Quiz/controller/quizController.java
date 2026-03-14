package English_Quiz.controller;

import English_Quiz.service.quizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class quizController {
    private final quizService quizService;

    public quizController(quizService quizService) {
        this.quizService = quizService;
    }
    @GetMapping("/quiz/{levelId}")
    public String quiz(@PathVariable int levelId, Model model) {
        model.addAttribute("questions", quizService.getQuestions(levelId));
        return "quiz";
    }
    @PostMapping("/result")
    public String result() {
        return "result";
    }
}