package English_Quiz.repository;

import English_Quiz.model.answer;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface answerRepository extends CrudRepository<answer, Integer> {
    List<answer> findByQuestionId(int questionId);
}