package English_Quiz.service;

import English_Quiz.model.Question;
import English_Quiz.model.Answer;
import English_Quiz.repository.QuestionRepository;
import English_Quiz.repository.AnswerRepository;

import org.springframework.stereotype.Service;

import java.util.List;

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
}