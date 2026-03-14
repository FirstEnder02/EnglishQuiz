package English_Quiz.repository;

import English_Quiz.model.Question;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    List<Question> findByLevelId(int levelId);
}