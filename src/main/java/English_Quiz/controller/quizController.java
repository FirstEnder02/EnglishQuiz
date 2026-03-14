package English_Quiz.controller;

import English_Quiz.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
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