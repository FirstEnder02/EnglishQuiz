package English_Quiz.repository;

import English_Quiz.model.level;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface levelRepository extends CrudRepository<level, Integer> {
    List<level> findByCategoryId(int categoryId);
}