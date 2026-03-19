package English_Quiz.service;

import English_Quiz.model.Question;
import English_Quiz.model.Answer;
import English_Quiz.repository.QuestionRepository;
import English_Quiz.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuestionRepository questionRepo;
    private final AnswerRepository answerRepo;

    public QuizService(QuestionRepository questionRepo, AnswerRepository answerRepo) {
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
    }

    public List<Question> getQuestions(int levelId) {
        List<Question> questions = questionRepo.findByLevelId(levelId);

        for (Question q : questions) {
            List<Answer> answers = answerRepo.findByQuestionId(q.getId());
            q.setAnswers(answers);
        }
        return questions;
    }

    public QuizResult gradeAnswers(Map<String, String> params) {
        Map<Integer, Integer> submitted = new HashMap<>();
        for (Map.Entry<String, String> e : params.entrySet()) {
            String key = e.getKey();
            if (key == null) continue;
            String lower = key.toLowerCase(Locale.ROOT).trim();
            if (lower.startsWith("q")) {
                String idPart = lower.substring(1);
                if (idPart.matches("\\d+")) {
                    try {
                        int qId = Integer.parseInt(idPart);
                        int aId = Integer.parseInt(e.getValue());
                        submitted.put(qId, aId);
                    } catch (NumberFormatException ex) {
                    }
                }
            }
        }

        if (submitted.isEmpty()) {
            return new QuizResult(0, 0, Collections.emptyMap());
        }
        List<Integer> qIds = new ArrayList<>(submitted.keySet());
        List<Question> questions = questionRepo.findAllById(qIds);

        int total = questions.size();
        int score = 0;
        Map<Integer, Boolean> details = new LinkedHashMap<>();

        for (Question q : questions) {
            int qId = q.getId();
            Integer chosenAnswerId = submitted.get(qId);
            List<Answer> answers = q.getAnswers();
            if (answers == null || answers.isEmpty()) {
                answers = answerRepo.findByQuestionId(qId);
            }

            List<Answer> correctAnswers = answers.stream()
                    .filter(Answer::isCorrect)
                    .collect(Collectors.toList());

            boolean correct = false;
            if (chosenAnswerId != null) {
                for (Answer a : correctAnswers) {
                    if (a.getId() == chosenAnswerId) {
                        correct = true;
                        break;
                    }
                }
            } else {
                correct = false;
            }

            if (correct) score++;
            details.put(qId, correct);
        }

        return new QuizResult(score, total, details);
    }
    public static class QuizResult {
        private final int score;
        private final int total;
        private final Map<Integer, Boolean> details;

        public QuizResult(int score, int total, Map<Integer, Boolean> details) {
            this.score = score;
            this.total = total;
            this.details = details;
        }

        public int getScore() {
            return score;
        }

        public int getTotal() {
            return total;
        }

        public Map<Integer, Boolean> getDetails() {
            return details;
        }
    }
}