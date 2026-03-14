package English_Quiz.repository;

import English_Quiz.model.question;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface questionRepository extends CrudRepository<question, Integer> {
    List<question> findByLevelId(int levelId);
}