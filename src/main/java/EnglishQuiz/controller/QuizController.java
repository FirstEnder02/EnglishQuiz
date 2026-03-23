package EnglishQuiz.controller;

import EnglishQuiz.model.Answer;
import EnglishQuiz.model.Question;
import EnglishQuiz.service.QuizService;
import EnglishQuiz.service.QuizService.RichResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz/{levelId}")
    public String quiz(@PathVariable int levelId, Model model) {
        List<Question> questions = quizService.getQuestions(levelId);

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < questions.size(); i++) {
            if (i > 0) json.append(",");
            Question q = questions.get(i);
            json.append("{")
                .append("\"id\":").append(q.getId()).append(",")
                .append("\"title\":").append(jsonString(q.getTitle())).append(",")
                .append("\"type\":").append(jsonString(q.getType() == null ? "S" : q.getType().toUpperCase())).append(",")
                .append("\"mediaUrl\":").append(jsonString(q.getMediaUrl())).append(",")
                .append("\"explaination\":").append(jsonString(q.getExplaination())).append(",")
                .append("\"answers\":[");
            List<Answer> answers = q.getAnswers();
            if (answers != null) {
                for (int j = 0; j < answers.size(); j++) {
                    if (j > 0) json.append(",");
                    Answer a = answers.get(j);
                    json.append("{")
                        .append("\"id\":").append(a.getId()).append(",")
                        .append("\"label\":").append(jsonString(a.getLabel())).append(",")
                        .append("\"text\":").append(jsonString(a.getText()))
                        .append("}");
                }
            }
            json.append("]}");
        }
        json.append("]");

        model.addAttribute("questionsJson", json.toString());
        model.addAttribute("levelId", levelId);
        model.addAttribute("total", questions.size());
        return "quiz";
    }

    @PostMapping("/result")
    public String result(@RequestParam MultiValueMap<String, String> allParams, Model model) {
        String levelIdStr = allParams.getFirst("levelId");
        int levelId = 0;
        if (levelIdStr != null) {
            try { levelId = Integer.parseInt(levelIdStr); } catch (NumberFormatException ignored) {}
        }

        RichResult result = quizService.gradeAnswers(allParams, levelId);
        model.addAttribute("result", result);
        model.addAttribute("score", result.getScore());
        model.addAttribute("total", result.getTotal());
        return "result";
    }

    private String jsonString(String s) {
        if (s == null) return "null";
        return "\"" + s
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
            + "\"";
    }
}