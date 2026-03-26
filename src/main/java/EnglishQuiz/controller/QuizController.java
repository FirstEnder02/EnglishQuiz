package EnglishQuiz.controller;

import EnglishQuiz.dto.QuizSession;
import EnglishQuiz.model.Question;
import EnglishQuiz.service.QuizService;
import EnglishQuiz.service.QuizService.RichResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz/{levelId}")
    public String startQuiz(@PathVariable int levelId, HttpSession session, Model model) {
        List<Question> questions = quizService.getQuestions(levelId);
        
        if (questions == null || questions.isEmpty()) {
            model.addAttribute("error", "No questions found for this level");
            return "redirect:/";
        }
        
        QuizSession quizSession = new QuizSession();
        quizSession.setLevelId(levelId);
        quizSession.setQuestionIds(questions.stream()
            .map(Question::getId)
            .collect(Collectors.toList()));
        quizSession.setCurrentIndex(0);
        
        session.setAttribute("quizSession", quizSession);
        return "redirect:/quiz/question";
    }

    @GetMapping("/quiz/question")
    public String showQuestion(HttpSession session, Model model) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");
        if (quizSession == null || quizSession.getQuestionIds().isEmpty()) {
            return "redirect:/";
        }

        int currentIndex = quizSession.getCurrentIndex();
        int questionId = quizSession.getQuestionIds().get(currentIndex);
        Question question = quizService.getQuestionById(questionId);

        if (question == null) {
            return "redirect:/";
        }

        List<Integer> selectedAnswers = quizSession.getAnswers()
            .getOrDefault(questionId, new ArrayList<>());

        model.addAttribute("question", question);
        model.addAttribute("currentIndex", currentIndex);
        model.addAttribute("totalQuestions", quizSession.getTotalQuestions());
        model.addAttribute("selectedAnswers", selectedAnswers);
        model.addAttribute("answeredCount", quizSession.getAnsweredCount());
        model.addAttribute("quizSession", quizSession);
        model.addAttribute("isLastQuestion", currentIndex == quizSession.getTotalQuestions() - 1);
        
        return "quiz";
    }

    @PostMapping("/quiz/answer")
    public String saveAnswer(@RequestParam(required = false) List<Integer> answerIds,
                           @RequestParam String action,
                           HttpSession session) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");
        if (quizSession == null) {
            return "redirect:/";
        }

        int currentIndex = quizSession.getCurrentIndex();
        int questionId = quizSession.getQuestionIds().get(currentIndex);
        
        if (answerIds != null && !answerIds.isEmpty()) {
            quizSession.getAnswers().put(questionId, new ArrayList<>(answerIds));
        } else {
            quizSession.getAnswers().put(questionId, new ArrayList<>());
        }

        if ("next".equals(action) && currentIndex < quizSession.getTotalQuestions() - 1) {
            quizSession.setCurrentIndex(currentIndex + 1);
            return "redirect:/quiz/question";
        } else if ("previous".equals(action) && currentIndex > 0) {
            quizSession.setCurrentIndex(currentIndex - 1);
            return "redirect:/quiz/question";
        } else if ("submit".equals(action)) {
            return "redirect:/quiz/result";
        } else if (action.startsWith("goto-")) {
            try {
                int targetIndex = Integer.parseInt(action.substring(5));
                if (targetIndex >= 0 && targetIndex < quizSession.getTotalQuestions()) {
                    quizSession.setCurrentIndex(targetIndex);
                }
            } catch (NumberFormatException ignored) {}
            return "redirect:/quiz/question";
        }

        return "redirect:/quiz/question";
    }

    @GetMapping("/quiz/result")
    public String showResult(HttpSession session, Model model) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");
        if (quizSession == null) {
            return "redirect:/";
        }

        RichResult result = quizService.gradeAnswers(quizSession);
        model.addAttribute("result", result);
        model.addAttribute("score", result.getScore());
        model.addAttribute("total", result.getTotal());
        
        session.removeAttribute("quizSession");
        return "result";
    }
}