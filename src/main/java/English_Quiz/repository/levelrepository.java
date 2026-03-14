package English_Quiz.repository;

import English_Quiz.model.Level;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LevelRepository extends CrudRepository<Level, Integer> {
    List<Level> findByCategoryId(int categoryId);
}