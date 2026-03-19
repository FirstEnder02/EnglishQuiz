package English_Quiz.controller;

import English_Quiz.service.QuizService;
import English_Quiz.service.QuizService.QuizResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz/{levelId}")
    public String quiz(@PathVariable int levelId, Model model) {
        model.addAttribute("questions", quizService.getQuestions(levelId));
        model.addAttribute("levelId", levelId);
        return "quiz";
    }

    @PostMapping("/result")
    public String result(@RequestParam Map<String, String> allParams, Model model) {
        QuizResult result = quizService.gradeAnswers(allParams);
        model.addAttribute("score", result.getScore());
        model.addAttribute("total", result.getTotal());
        model.addAttribute("details", result.getDetails()); // optional per-question feedback
        return "result";
    }
}