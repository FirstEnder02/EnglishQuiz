package English_Quiz.repository;

import English_Quiz.model.Answer;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    List<Answer> findByQuestionId(int questionId);
}