package English_Quiz.service;

import English_Quiz.model.question;
import English_Quiz.model.answer;
import English_Quiz.repository.questionRepository;
import English_Quiz.repository.answerRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class quizService {
    private final questionRepository questionRepo;
    private final answerRepository answerRepo;

    public quizService(questionRepository questionRepo, answerRepository answerRepo) {
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
    }
    public List<question> getQuestions(int levelId) {
        List<question> questions = questionRepo.findByLevelId(levelId);

        for (question q : questions) {
            List<answer> answers = answerRepo.findByQuestionId(q.getId());
            q.setAnswers(answers);
        }
        return questions;
    }
}